package com.example.wedrivetest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.wedrive.feature.addcard.navigation.addCardScreen
import com.example.wedrive.feature.addcard.navigation.navigateToAddCard
import com.example.wedrive.feature.wallet.navigation.WalletRoute
import com.example.wedrive.feature.wallet.navigation.walletScreen

@Composable
fun WeDriveNavHost(
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WalletRoute
    ) {
        walletScreen(
            onShowSnackbar = onShowSnackbar,
            onAddCardClick = navController::navigateToAddCard
        )

        addCardScreen(
            onShowSnackbar = onShowSnackbar,
            onBackClick = navController::popBackStack
        )
    }
}