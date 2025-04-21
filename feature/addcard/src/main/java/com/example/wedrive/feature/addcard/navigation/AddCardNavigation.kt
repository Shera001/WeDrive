package com.example.wedrive.feature.addcard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.wedrive.feature.addcard.AddCardRoute
import kotlinx.serialization.Serializable

@Serializable
object AddCardRoute

fun NavController.navigateToAddCard() = navigate(route = AddCardRoute)

fun NavGraphBuilder.addCardScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit
) {
    composable<AddCardRoute> {
        AddCardRoute(onShowSnackbar, onBackClick)
    }
}