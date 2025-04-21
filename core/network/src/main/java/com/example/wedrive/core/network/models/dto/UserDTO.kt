package com.example.wedrive.core.network.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("id") val id: Int,
    @SerialName("phone") val phone: String,
    @SerialName("balance") val balance: Long,
    @SerialName("active_method") val activeMethod: String,
    @SerialName("active_card_id") val activeCardId: Int?
)
