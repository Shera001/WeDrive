package com.example.wedrive.feature.wallet.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.wedrive.feature.wallet.WalletRoute
import kotlinx.serialization.Serializable

@Serializable
object WalletRoute

fun NavGraphBuilder.walletScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onAddCardClick: () -> Unit
) {
    composable<WalletRoute> {
        WalletRoute(
            onShowSnackbar = onShowSnackbar,
            onAddCardClick = onAddCardClick
        )
    }
}