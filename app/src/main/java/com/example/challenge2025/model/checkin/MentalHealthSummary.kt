package com.example.challenge2025.model.checkin

import androidx.compose.ui.graphics.vector.ImageVector

// Representa a meta de "Saúde da Mente"
data class MentalHealthSummary(
    val title: String = "Saúde da mente",
    val icon: ImageVector,
    val healthPercentage: Int,
    val positiveEmotions: Int,
    val negativeEmotions: Int
)

// Representa a meta de "Dias de Cuidado"
data class CareDaysGoal(
    val title: String = "Dias de cuidado",
    val icon: ImageVector,
    val completedDays: Int,
    val goalDays: Int
)