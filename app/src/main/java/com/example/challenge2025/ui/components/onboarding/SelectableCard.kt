// ui/components/onboarding/SelectableCard.kt
package com.example.challenge2025.ui.components.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SelectableCard(
    text: String,
    isSelected: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null
) {
    val isDarkTheme = isSystemInDarkTheme()

    val containerColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.8f) // Cor quando selecionado (seu "Roxo")
        } else if (isDarkTheme) {
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f) // Tema escuro
        } else {
            MaterialTheme.colorScheme.surface // Tema claro
        },
        animationSpec = tween(durationMillis = 200), label = "cardContainerColorAnimation"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.onPrimary // Texto branco ou contrastante quando selecionado
        } else if (isDarkTheme) {
            MaterialTheme.colorScheme.onSurface // Texto em tema escuro
        } else {
            MaterialTheme.colorScheme.onSurface // Texto em tema claro
        },
        animationSpec = tween(durationMillis = 200), label = "cardContentColorAnimation"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary // Borda da cor principal quando selecionado
        } else if (isDarkTheme) {
            Color.Transparent // Sem borda visível no tema escuro flutuante
        } else {
            MaterialTheme.colorScheme.outline.copy(alpha = 0.4f) // Borda suave no tema claro
        },
        animationSpec = tween(durationMillis = 200), label = "cardBorderColorAnimation"
    )

    val cardElevation = if (isDarkTheme) {
        CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 4.dp)
    } else {
        CardDefaults.cardElevation(defaultElevation = 0.dp) // Sem sombra no tema claro
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp) // Altura mínima para o card
            .clickable(onClick = onCardClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor, contentColor = contentColor),
        elevation = cardElevation,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                icon?.invoke() // Renderiza o ícone se fornecido
                if (icon != null) Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f) // O texto pode expandir
                )
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selecionado",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}