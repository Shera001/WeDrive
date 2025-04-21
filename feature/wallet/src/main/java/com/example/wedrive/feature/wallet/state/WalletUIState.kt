package com.example.wedrive.feature.wallet.state

data class WalletUIState(
    val isLoading: Boolean = true,
    val showBottomSheet: Boolean = false,
    val balance: Long = 0,
    val activeMethod: WalletSource = WalletSource.None,
    val cards: List<CardUI> = emptyList(),
)