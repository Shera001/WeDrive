package com.example.wedrive.core.network.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class PaymentMethodRequest {

    @Serializable
    data class Cash(
        @SerialName("active_method") val activeMethod: String,
    ) : PaymentMethodRequest()

    @Serializable
    data class Card(
        @SerialName("active_card_id") val activeCardId: Int?,
        @SerialName("active_method") val activeMethod: String,
    ) : PaymentMethodRequest()
}