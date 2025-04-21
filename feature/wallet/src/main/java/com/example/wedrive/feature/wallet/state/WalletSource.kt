package com.example.wedrive.feature.wallet.state

sealed class WalletSource(
    open val activeCardId: Int? = null
) {
    data object None: WalletSource()

    data object Cash : WalletSource()

    data class Card(override val activeCardId: Int?) : WalletSource()
}