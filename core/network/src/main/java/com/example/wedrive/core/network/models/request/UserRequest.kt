package com.example.wedrive.core.network.models.request

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val phone: String = "+998901234567"
)
