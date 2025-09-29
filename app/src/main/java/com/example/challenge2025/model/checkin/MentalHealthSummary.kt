package com.example.challenge2025.model.checkin

import androidx.compose.ui.graphics.vector.ImageVector

data class MentalHealthSummary(
    val title: String = "Saúde da mente",
    val icon: ImageVector,
    val healthPercentage: Int,
    val positiveEmotions: Int,
    val negativeEmotions: Int
)

data class CareDaysGoal(
    val title: String = "Dias de cuidado",
    val icon: ImageVector,
    val completedDays: Int,
    val goalDays: Int
)