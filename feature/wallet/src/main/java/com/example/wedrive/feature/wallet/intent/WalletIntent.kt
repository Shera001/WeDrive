package com.example.wedrive.feature.wallet.intent

import com.example.wedrive.feature.wallet.state.WalletSource

sealed class WalletIntent {

    data object Init : WalletIntent()

    data object ShowAddPromoCodeDialog : WalletIntent()

    data object HideAddPromoCodeDialog : WalletIntent()

    data class AddPromoCode(val code: String) : WalletIntent()

    data class UpdateWalletSource(val walletSource: WalletSource) : WalletIntent()
}