package com.example.wedrive.core.network.service.auth

import com.example.wedrive.core.network.models.dto.UserDTO
import com.example.wedrive.core.network.models.request.UserRequest
import com.example.wedrive.core.network.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val client: HttpClient
) : AuthService {
    override suspend fun createUser(request: UserRequest): Result<UserDTO> {
        return safeApiCall {
            client.post("users") {
                setBody(request)
            }.body()
        }
    }
}