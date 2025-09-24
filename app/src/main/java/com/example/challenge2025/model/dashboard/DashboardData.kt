package com.example.challenge2025.model.dashboard

import com.example.challenge2025.model.user.Feeling
import com.example.challenge2025.model.user.UserCheckin
import com.example.challenge2025.model.user.UserTestResult


enum class TimePeriod {
    WEEKLY,
    MONTHLY,
    YEARLY
}

// Representa uma única fatia do gráfico de sentimentos
data class FeelingPercentage(
    val feeling: Feeling,
    val percentage: Float,
    val count: Int,
    val color: String
)

// NOVA: Estrutura para o gráfico de sentimentos do período
data class PeriodFeelingSummary(
    val mostFrequentFeeling: Feeling?,
    val feelingPercentages: List<FeelingPercentage>
)

// AJUSTADA: Agora com Depressão e tendências para todos os itens
data class WellbeingPanel(
    val burnoutRisk: String,
    val anxietyLevel: String,
    val depressionLevel: String, // Substituiu "Humor"
    val burnoutTrend: Trend = Trend.STABLE,
    val anxietyTrend: Trend = Trend.STABLE,
    val depressionTrend: Trend = Trend.STABLE
) {
    enum class Trend {
        UP, DOWN, STABLE
    }
}

// NOVA: Estrutura para o gráfico de linha de tendências
data class PsychosocialTrend(
    // O Map guardará uma lista de pontos para cada tipo de teste
    // Ex: "Ansiedade" -> [DataPoint(timestamp, score), DataPoint(timestamp, score), ...]
    val trends: Map<String, List<Float>>
) {
    data class DataPoint(val timestamp: Long, val score: Int)
}


// A data class "mãe" que o backend retornará.
data class DashboardData(
    val wellbeingPanel: WellbeingPanel,
    val recommendations: List<Recommendation>,
    val activityHistory: List<UserCheckin>,
    val periodFeelingSummary: PeriodFeelingSummary, // SUBSTITUÍDO: EmotionalJourney -> PeriodFeelingSummary
    val psychosocialTrend: PsychosocialTrend, // NOVO: Adicionado o modelo de dados de tendência
    val riskProfile: RiskProfile
)



data class RiskProfile(
    val radarChartData: Map<String, Float>,
    val historicalData: List<UserTestResult>
)

data class Recommendation(
    val id: String,
    val title: String,
    val description: String,
    val actionText: String
)


