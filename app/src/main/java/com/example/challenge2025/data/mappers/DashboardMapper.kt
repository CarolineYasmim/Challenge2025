// ARQUIVO MODIFICADO: data/mappers/DashboardMapper.kt

package com.example.challenge2025.data.mappers

import com.example.challenge2025.data.remote.dto.sentimentos.EstatisticaSentimentoResponseDto
import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.domain.model.checkin.FeelingsData
import com.example.challenge2025.domain.model.dashboard.FeelingPercentage
import com.example.challenge2025.domain.model.dashboard.PeriodFeelingSummary
import com.example.challenge2025.domain.model.dashboard.PsychosocialTrend
import com.example.challenge2025.domain.model.dashboard.RiskProfile
import com.example.challenge2025.domain.model.dashboard.WellbeingPanel

// MUDANÇA 1: Renomeamos a função para ser mais específica e evitar conflito
fun EstatisticaSentimentoResponseDto.toPeriodFeelingSummaryModel(): PeriodFeelingSummary {
    val feelingPercentages = this.details.map { detail ->
        val feelingColor = FeelingsData.availableFeelings
            .find { f -> f.name.equals(detail.feeling, ignoreCase = true) }
            ?.colorHex ?: "#808080"

        FeelingPercentage(
            feelingName = detail.feeling,
            percentage = detail.percentage.toFloat(),
            count = detail.count,
            color = feelingColor
        )
    }

    return PeriodFeelingSummary(
        mostFrequentFeeling = this.frequentFeeling,
        feelingPercentages = feelingPercentages
    )
}

// --- Tradutores para os Resultados dos Testes ---

fun List<ResultadoResponseDto>.toRiskProfileModel(): RiskProfile {
    val latestResults = this
        .sortedByDescending { it.completionDate }
        .distinctBy { it.testType }

    val radarData = latestResults.associate {
        it.testType to it.totalScore
    }
    return RiskProfile(radarChartData = radarData)
}

fun List<ResultadoResponseDto>.toPsychosocialTrendModel(): PsychosocialTrend {
    val trendsData = this
        .groupBy { it.testType }
        .mapValues { entry ->
            entry.value
                .sortedBy { it.completionDate }
                .map { it.totalScore }
        }
    return PsychosocialTrend(trends = trendsData)
}

fun List<ResultadoResponseDto>.toWellbeingPanelModel(): WellbeingPanel {
    val anxietyResult = this.filter { it.testType.equals("ANSIEDADE", true) }.maxByOrNull { it.completionDate }
    val burnoutResult = this.filter { it.testType.equals("BURNOUT", true) }.maxByOrNull { it.completionDate }
    val depressionResult = this.filter { it.testType.equals("DEPRESSÃO", true) }.maxByOrNull { it.completionDate }

    return WellbeingPanel(
        anxietyLevel = anxietyResult?.riskLevel ?: "N/A",
        burnoutRisk = burnoutResult?.riskLevel ?: "N/A",
        depressionLevel = depressionResult?.riskLevel ?: "N/A",
        anxietyTrend = WellbeingPanel.Trend.STABLE,
        burnoutTrend = WellbeingPanel.Trend.STABLE,
        depressionTrend = WellbeingPanel.Trend.STABLE
    )
}