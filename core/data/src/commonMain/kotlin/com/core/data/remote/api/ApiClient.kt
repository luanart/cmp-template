package com.core.data.remote.api

import com.core.common.error.ApiError
import com.core.common.error.AppException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class ApiClient(
    private val engine: HttpClientEngine,
    private val isDebug: Boolean,
    private val tokenManager: TokenManager
) {
    val client: HttpClient by lazy {
        HttpClient(engine) {
            expectSuccess = true

            install(HttpTimeout) {
                connectTimeoutMillis = 10_000
                requestTimeoutMillis = 60_000
                socketTimeoutMillis = 30_000
            }

            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    explicitNulls = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    allowStructuredMapKeys = true
                })
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        tokenManager.loadTokens()
                    }

                    refreshTokens {
                        tokenManager.refreshTokens(oldTokens)
                    }

                    sendWithoutRequest { request ->
                        val url = Url(ApiConfig.baseUrl)
                        request.url.host == url.host && request.url.protocol == url.protocol
                    }
                }
            }

            install(Logging) {
                level = if (isDebug) LogLevel.ALL else LogLevel.NONE
                logger = Logger.SIMPLE
            }

            defaultRequest {
                url(ApiConfig.baseUrl)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, _ ->
                    if (cause !is ResponseException) throw cause
                    throw runCatching { cause.response.body<ApiError>() }.getOrNull()
                        ?.let { AppException.Api(it) }
                        ?: cause
                }
            }
        }
    }
}