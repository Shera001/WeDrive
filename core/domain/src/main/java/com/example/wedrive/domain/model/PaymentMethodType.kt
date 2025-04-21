package com.example.wedrive.domain.model

sealed class PaymentMethodType {

    data object Cash : PaymentMethodType()

    data class Card(val activeCardId: Int?) : PaymentMethodType()
}