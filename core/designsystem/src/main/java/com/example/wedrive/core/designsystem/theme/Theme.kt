package com.example.wedrive.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    primaryContainer = PrimaryContainer,
    secondaryContainer = SecondaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    tertiaryContainer = TertiaryContainer,
    outline = Outline,
    surface = Surface,
    onSurface = OnSurface,
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    primaryContainer = PrimaryContainer,
    secondaryContainer = SecondaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    tertiaryContainer = TertiaryContainer,
    outline = Outline,
    surface = Surface,
    onSurface = OnSurface,
)

@Composable
fun WeDriveTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}