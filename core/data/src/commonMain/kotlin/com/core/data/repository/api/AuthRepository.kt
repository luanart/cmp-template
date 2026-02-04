package com.core.data.repository.api

import com.core.data.remote.dto.LoginRequestDto
import com.core.data.remote.dto.UserDto

interface AuthRepository {
    suspend fun login(data: LoginRequestDto) : Result<Unit>
    suspend fun fetchCurrentProfile() : Result<UserDto>
}