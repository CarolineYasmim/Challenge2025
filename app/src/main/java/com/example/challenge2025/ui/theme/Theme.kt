package com.example.challenge2025.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color

// Light ColorScheme
private val LightColors = lightColorScheme(
    primary = Blue,
    secondary = Green,
    tertiary = Orange,
    background = Color(0xFFF3F3F6), // <-- NOVO: Fundo cinza claro
    surface = White,                // <-- NOVO: Cartões e superfícies brancas
    surfaceVariant = White,         // <-- NOVO: Cartões e superfícies brancas
    onSurface = Black,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White
)

// Dark ColorScheme
private val DarkColors = darkColorScheme(
    primary = Blue,
    secondary = Green,
    tertiary = Orange,
    background = Black,
    surface = GrayDark,
    surfaceVariant = GrayDark,
    onSurface = White,
    onPrimary = White,
    onSecondary = Black,
    onTertiary = Black
)

@Composable
fun Challenge2025Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}