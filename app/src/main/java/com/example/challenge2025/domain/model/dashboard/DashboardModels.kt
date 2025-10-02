// ARQUIVO NOVO: domain/model/dashboard/DashboardModels.kt

package com.example.challenge2025.domain.model.dashboard

enum class TimePeriod {
    WEEKLY, MONTHLY, YEARLY
}

// Modelo principal que agrupa todos os dados do dashboard
data class DashboardData(
    val wellbeingPanel: WellbeingPanel,
    val periodFeelingSummary: PeriodFeelingSummary,
    val recommendations: List<Recommendation>,
    val psychosocialTrend: PsychosocialTrend,
    val riskProfile: RiskProfile
)

// Modelo para o componente WellbeingPanel
data class WellbeingPanel(
    val burnoutRisk: String,
    val anxietyLevel: String,
    val depressionLevel: String,
    val burnoutTrend: Trend,
    val anxietyTrend: Trend,
    val depressionTrend: Trend
) {
    enum class Trend { UP, DOWN, STABLE }
}

// Modelo para o componente PeriodFeelingCard
data class PeriodFeelingSummary(
    val mostFrequentFeeling: String,
    val feelingPercentages: List<FeelingPercentage>
)

data class FeelingPercentage(
    val feelingName: String,
    val percentage: Float,
    val count: Int,
    val color: String // Cor em hexadecimal, ex: "#FFD93D"
)

// Modelo para o componente Recommendations
data class Recommendation(
    val id: String,
    val title: String,
    val description: String,
    val actionText: String
)

// Modelo para o componente PsychosocialTrendCard
data class PsychosocialTrend(
    val trends: Map<String, List<Int>> // Ex: "Ansiedade" -> [12, 14, 11, 10, 9]
)

// Modelo para o componente RiskProfileCard
data class RiskProfile(
    val radarChartData: Map<String, Int> // Ex: "Ansiedade" -> 9
)