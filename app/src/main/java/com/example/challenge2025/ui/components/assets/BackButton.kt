package com.example.challenge2025.ui.components.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
    val borderColor = if (isDark) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f) else Color.Transparent
    val iconColor = if (isDark) Color.White else Color.Black

    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(54.dp)
            .border(
                width = if (isDark) 1.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(27.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(27.dp)
            )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Voltar",
            tint = iconColor
        )
    }
}
