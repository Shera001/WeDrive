package com.example.wedrive.core.data.mapper

import com.example.wedrive.core.network.models.dto.CardDTO
import com.example.wedrive.core.network.models.dto.UserDTO
import com.example.wedrive.core.network.models.request.PaymentMethodRequest
import com.example.wedrive.domain.model.Card
import com.example.wedrive.domain.model.PaymentMethodType
import com.example.wedrive.domain.model.User

fun PaymentMethodType.toPaymentMethodRequest(): PaymentMethodRequest {
    return when (this) {
        PaymentMethodType.Cash -> PaymentMethodRequest.Cash(activeMethod = "cash")
        is PaymentMethodType.Card -> PaymentMethodRequest.Card(
            activeCardId = activeCardId,
            activeMethod = "card"
        )
    }
}

fun UserDTO.toUser(): User {
    return User(
        id = id,
        phone = phone,
        balance = balance,
        activeMethod = if (activeMethod.equals("cash", true)) {
            PaymentMethodType.Cash
        } else {
            PaymentMethodType.Card(activeCardId)
        }
    )
}

fun CardDTO.toCard(): Card {
    return Card(
        id = id,
        number = number,
        expireDate = expireDate,
        userId = userId
    )
}