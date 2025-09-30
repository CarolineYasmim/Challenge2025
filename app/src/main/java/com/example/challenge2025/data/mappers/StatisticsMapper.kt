package com.example.challenge2025.data.mappers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Psychology
import com.example.challenge2025.data.remote.dto.checkin.EstatisticaSentimentoResponseDto
import com.example.challenge2025.domain.model.statistics.CareDaysGoal
import com.example.challenge2025.domain.model.statistics.MentalHealthSummary
import com.example.challenge2025.domain.model.statistics.StatisticsSummary

fun EstatisticaSentimentoResponseDto.toDomainModel(): StatisticsSummary {
    // Lógica para calcular os dados. Isso é um exemplo, você pode ajustar.
    val totalCount = this.details.sumOf { it.count }
    val positiveCount = this.details.filter { it.feeling in listOf("MOTIVADO", "SATISFEITO", "ANIMADO") }.sumOf { it.count }
    val negativeCount = totalCount - positiveCount

    // Exemplo de meta de dias de cuidado
    val careGoalDays = 7

    val mentalHealth = MentalHealthSummary(
        icon = Icons.Rounded.Psychology,
        healthPercentage = ((positiveCount.toFloat() / totalCount.toFloat()) * 100).toInt(),
        positiveEmotions = positiveCount,
        negativeEmotions = negativeCount
    )

    val careGoal = CareDaysGoal(
        icon = Icons.Rounded.FavoriteBorder,
        completedDays = totalCount, // Assumindo 1 check-in por dia
        goalDays = careGoalDays
    )

    return StatisticsSummary(
        mentalHealth = mentalHealth,
        careGoal = careGoal
    )
}