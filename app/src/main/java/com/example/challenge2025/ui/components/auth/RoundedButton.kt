package com.example.challenge2025.ui.components.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isOutlined: Boolean = false,
    enabled: Boolean = true, // NOVO PARÂMETRO
    content: (@Composable RowScope.() -> Unit)? = null // NOVO PARÂMETRO PARA CONTEÚDO CUSTOMIZADO
) {
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            enabled = enabled, // PARÂMETRO APLICADO
            modifier = modifier
                .widthIn(min = 280.dp)
                .height(50.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            // Se o conteúdo customizado for fornecido, use-o. Senão, use o texto.
            if (content != null) {
                content()
            } else {
                Text(
                    text,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    } else {
        Button(
            onClick = onClick,
            enabled = enabled, // PARÂMETRO APLICADO
            modifier = modifier
                .widthIn(min = 280.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            if (content != null) {
                content()
            } else {
                Text(
                    text,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}