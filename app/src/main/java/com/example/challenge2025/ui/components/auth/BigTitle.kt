package com.example.challenge2025.ui.components.auth

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun BigTitle(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        fontSize = 28.sp, // Um pouco maior para destaque
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary, // Usa a cor primária do seu tema
        modifier = modifier,
        textAlign = TextAlign.Center,
        lineHeight = 34.sp
    )
}