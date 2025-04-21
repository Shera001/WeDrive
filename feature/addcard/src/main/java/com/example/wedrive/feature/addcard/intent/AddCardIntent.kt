package com.example.wedrive.feature.addcard.intent

import com.example.wedrive.feature.addcard.CardFieldType

sealed class AddCardIntent {

    data class OnCardNumberChange(val number: String) : AddCardIntent()

    data class OnCardDateChange(val date: String) : AddCardIntent()

    data class OnNumberClick(val number: String) : AddCardIntent()

    data object OnRemoveNumberClick : AddCardIntent()

    data object OnAddCardClick : AddCardIntent()

    data class ChangeCardFieldType(val type: CardFieldType) : AddCardIntent()
}