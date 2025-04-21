package com.example.wedrive.feature.wallet.event

sealed class WalletEvent {

    data class ShowMessage(val message: String) : WalletEvent()

    data object OnInitError : WalletEvent()

    data class OnAddPromoCodeError(val message: String) : WalletEvent()
}