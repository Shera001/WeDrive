package com.example.wedrive.domain.model

data class User(
    val id: Int,
    val phone: String,
    val balance: Long,
    val activeMethod: PaymentMethodType,
)
