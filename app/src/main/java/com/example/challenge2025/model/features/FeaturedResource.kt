package com.example.challenge2025.model.features

import androidx.compose.ui.graphics.vector.ImageVector

// Representa o card principal em destaque
data class FeaturedResource(
    val title: String,
    val description: String,
    val buttonText: String,
    val icon: ImageVector
)

// Representa um dos botões de ação rápida
data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val type: QuickActionType
)

// Enum para diferenciar as ações e aplicar lógicas/estilos diferentes
enum class QuickActionType {
    DEFAULT,
    URGENT
}