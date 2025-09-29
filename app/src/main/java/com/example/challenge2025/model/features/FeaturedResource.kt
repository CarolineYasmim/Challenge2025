package com.example.challenge2025.model.features

import androidx.compose.ui.graphics.vector.ImageVector


data class FeaturedResource(
    val title: String,
    val description: String,
    val buttonText: String,
    val icon: ImageVector
)


data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val type: QuickActionType
)

enum class QuickActionType {
    DEFAULT,
    URGENT
}