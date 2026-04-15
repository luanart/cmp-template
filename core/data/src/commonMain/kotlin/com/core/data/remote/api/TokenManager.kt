package com.core.data.remote.api

import com.core.data.local.SecureStorage
import com.core.data.local.SecureStorage.Companion.clearToken
import com.core.data.local.SecureStorage.Companion.getAccessToken
import com.core.data.local.SecureStorage.Companion.getRefreshToken
import com.core.data.local.SecureStorage.Companion.storeAccessToken
import com.core.data.local.SecureStorage.Companion.storeRefreshToken
import com.core.data.remote.dto.AuthTokenDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

class TokenManager(
    private val engine: HttpClientEngine,
    private val storage: SecureStorage
) {

    private val refreshMutex = Mutex()
    private val refreshClient by lazy {
        HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    suspend fun loadTokens(): BearerTokens? {
        val accessToken = storage.getAccessToken() ?: return null
        return BearerTokens(
            accessToken = accessToken,
            refreshToken = storage.getRefreshToken()
        )
    }

    suspend fun refreshTokens(oldTokens: BearerTokens?): BearerTokens? {
        return refreshMutex.withLock {

            val currentAccessToken = storage.getAccessToken()
            val currentRefreshToken = storage.getRefreshToken()

            // 🔁 Already refreshed by another request
            if (
                currentAccessToken != null &&
                currentAccessToken != oldTokens?.accessToken
            ) {
                return@withLock BearerTokens(
                    accessToken = currentAccessToken,
                    refreshToken = currentRefreshToken
                )
            }

            val oldRefreshToken = oldTokens?.refreshToken ?: return@withLock null

            try {
                val response = refreshClient.post(ApiUrl.REFRESH_TOKEN) {
                    contentType(ContentType.Application.Json)
                    setBody(mapOf("refreshToken" to oldRefreshToken))
                }.body<AuthTokenDto>()

                // ✅ Store safely
                storage.storeAccessToken(response.accessToken)
                response.refreshToken?.let {
                    storage.storeRefreshToken(it)
                }

                BearerTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            } catch (e: Exception) {
                e.printStackTrace()
                storage.clearToken()
                null
            }
        }
    }
}