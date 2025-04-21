package com.example.wedrive.feature.wallet.state

import com.example.wedrive.domain.model.Card
import com.example.wedrive.domain.model.PaymentMethodType

fun PaymentMethodType.toWalletSource(): WalletSource {
    return when (this) {
        is PaymentMethodType.Card -> WalletSource.Card(activeCardId)
        PaymentMethodType.Cash -> WalletSource.Cash
    }
}

fun WalletSource.toPaymentMethod(): PaymentMethodType {
    return when (this) {
        is WalletSource.Card -> PaymentMethodType.Card(activeCardId)
        else -> PaymentMethodType.Cash
    }
}

fun Card.toCardUI() = CardUI(
    id = id,
    userId = userId,
    number = number,
    expireDate = expireDate,
)