package com.example.challenge2025.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Typography

// Light ColorScheme
private val LightColors = lightColorScheme(
    primary = Blue,
    secondary = Green,
    tertiary = Orange,
    background = White,
    surface = White,
    surfaceVariant = GrayLight,
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
    onPrimary = Black,
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
