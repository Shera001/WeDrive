package com.example.wedrive.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.wedrive.core.designsystem.R

val Inter = FontFamily(
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_regular, FontWeight.Normal)
)

val Figtree = FontFamily(
    Font(R.font.figtree_semibold, FontWeight.SemiBold),
    Font(R.font.figtree_medium, FontWeight.Medium)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        color = Primary,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
    ),
    bodyMedium = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Medium,
        color = Color.White,
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        color = OnSurface
    ),
    labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        color = Primary
    )
)