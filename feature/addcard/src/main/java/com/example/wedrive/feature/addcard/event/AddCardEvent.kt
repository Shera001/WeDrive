package com.example.wedrive.feature.addcard.event

sealed class AddCardEvent {

    data object OnSuccess : AddCardEvent()

    data class OnError(val message: String) : AddCardEvent()
}