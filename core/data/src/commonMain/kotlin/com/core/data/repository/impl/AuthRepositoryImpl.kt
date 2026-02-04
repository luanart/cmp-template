package com.core.data.repository.impl

import com.core.data.local.LocalStorage
import com.core.data.local.SecureStorage
import com.core.data.local.SecureStorage.Companion.storeAccessToken
import com.core.data.local.SecureStorage.Companion.storeRefreshToken
import com.core.data.remote.api.ApiConfig
import com.core.data.remote.api.ApiService
import com.core.data.remote.dto.LoginRequestDto
import com.core.data.remote.dto.LoginResponseDto
import com.core.data.remote.dto.UserDto
import com.core.data.repository.api.AuthRepository
import org.koin.core.annotation.Single

@Single(binds = [AuthRepository::class])
internal class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val localStorage: LocalStorage,
    private val secureStorage: SecureStorage
) : AuthRepository {
    override suspend fun login(data: LoginRequestDto) : Result<Unit> {
        return runCatching {
            val response = apiService.post<LoginResponseDto>(url = ApiConfig.Url.LOGIN, data = data)
            localStorage.storeUserId(response.userId)
            secureStorage.storeAccessToken(response.accessToken)
            secureStorage.storeRefreshToken(response.refreshToken)
        }
    }

    override suspend fun fetchCurrentProfile(): Result<UserDto> {
        return runCatching { apiService.get<UserDto>(url = ApiConfig.Url.CURRENT) }
    }
}