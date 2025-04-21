package com.example.wedrive.core.network.service.auth

import com.example.wedrive.core.network.models.dto.UserDTO
import com.example.wedrive.core.network.models.request.UserRequest

interface AuthService {

    suspend fun createUser(request: UserRequest = UserRequest()): Result<UserDTO>
}