package com.example.wedrive.core.network.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardDTO(
    @SerialName("id") val id: Int,
    @SerialName("number") val number: String,
    @SerialName("expire_date") val expireDate: String,
    @SerialName("user_id") val userId: Int
)