package com.example.wedrive.feature.addcard.state

import com.example.wedrive.feature.addcard.CardFieldType

data class AddCardState(
    val isLoading: Boolean = false,
    val saveButtonEnabled: Boolean = false,
    val number: String = "",
    val expireDate: String = "",
    val cardFieldType: CardFieldType = CardFieldType.NUMBER,
)
