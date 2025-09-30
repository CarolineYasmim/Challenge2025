package com.example.challenge2025.domain.model.statistics

import androidx.compose.ui.graphics.vector.ImageVector

data class StatisticsSummary(
    val mentalHealth: MentalHealthSummary,
    val careGoal: CareDaysGoal
)

data class MentalHealthSummary(
    val icon: ImageVector,
    val healthPercentage: Int,
    val positiveEmotions: Int,
    val negativeEmotions: Int
)

data class CareDaysGoal(
    val icon: ImageVector,
    val completedDays: Int,
    val goalDays: Int
)