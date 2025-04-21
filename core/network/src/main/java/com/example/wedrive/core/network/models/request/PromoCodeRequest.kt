package com.example.wedrive.core.network.models.request

import kotlinx.serialization.Serializable

@Serializable
data class PromoCodeRequest(
    val code: String
)
