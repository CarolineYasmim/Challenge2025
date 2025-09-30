package com.example.challenge2025.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.model.dashboard.DashboardData
import com.example.challenge2025.model.dashboard.FeelingPercentage
import com.example.challenge2025.model.dashboard.PeriodFeelingSummary
import com.example.challenge2025.model.dashboard.PsychosocialTrend
import com.example.challenge2025.model.dashboard.Recommendation
import com.example.challenge2025.model.dashboard.RiskProfile
import com.example.challenge2025.model.dashboard.TimePeriod
import com.example.challenge2025.model.dashboard.WellbeingPanel
import com.example.challenge2025.model.data.MockData
import com.example.challenge2025.model.checkin.UserCheckin
import com.example.challenge2025.model.user.UserTestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

// O estado da UI não muda
data class DashboardUiState(
    val selectedPeriod: TimePeriod = TimePeriod.WEEKLY,
    val dashboardData: DashboardData? = null,
    val isLoading: Boolean = true
)

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    private val mockTests = MockData.tests
    private val mockCheckinFeelings = Checkin.availableFeelings

    init {
        loadDashboardData(TimePeriod.WEEKLY)
    }

    fun setPeriod(period: TimePeriod) {
        if (period != _uiState.value.selectedPeriod) {
            loadDashboardData(period)
        }
    }

    private fun calculateRiskProfile(results: List<UserTestResult>): RiskProfile {
        // Pega o resultado mais recente de cada tipo de teste para o Radar
        val latestAnxiety = results.filter { it.testId == "1" }.maxByOrNull { it.timestamp }
        val latestBurnout = results.filter { it.testId == "2" }.maxByOrNull { it.timestamp }
        val latestDepression = results.filter { it.testId == "3" }.maxByOrNull { it.timestamp }

        val radarData = mutableMapOf<String, Float>()
        latestAnxiety?.let { radarData["Ansiedade"] = it.score.toFloat() }
        latestBurnout?.let { radarData["Burnout"] = it.score.toFloat() }
        latestDepression?.let { radarData["Depressão"] = it.score.toFloat() }

        return RiskProfile(
            radarChartData = radarData,
            historicalData = results // Histórico completo para detalhamento
        )
    }

    private fun loadDashboardData(period: TimePeriod) {
        _uiState.update { it.copy(isLoading = true, selectedPeriod = period) }

        viewModelScope.launch {
            val days = when (period) {
                TimePeriod.WEEKLY -> 7
                TimePeriod.MONTHLY -> 30
                TimePeriod.YEARLY -> 365
            }

            // 1. Gera dados brutos
            val checkins = generateMockCheckins(days)
            val testResults = generateMockTestResults(days)

            // 2. Chama as novas funções de cálculo
            val wellbeingPanel = calculateWellbeingPanel(testResults)
            val feelingSummary = calculatePeriodFeelingSummary(checkins)
            val psychosocialTrend = calculatePsychosocialTrend(testResults) // Chamada da função corrigida
            val recommendations = generateRecommendations(wellbeingPanel)
            val riskProfile = calculateRiskProfile(testResults)

            // 3. Monta o objeto DashboardData atualizado
            val dashboardData = DashboardData(
                wellbeingPanel = wellbeingPanel,
                recommendations = recommendations,
                activityHistory = checkins,
                periodFeelingSummary = feelingSummary,
                psychosocialTrend = psychosocialTrend,
                riskProfile = riskProfile
            )

            // 4. Atualiza o estado da UI
            _uiState.update {
                it.copy(dashboardData = dashboardData, isLoading = false)
            }
        }
    }

    // --- FUNÇÕES DE CÁLCULO ---

    private fun calculatePeriodFeelingSummary(checkins: List<UserCheckin>): PeriodFeelingSummary {
        if (checkins.isEmpty()) {
            return PeriodFeelingSummary(null, emptyList())
        }

        val allFeelingsRecorded = checkins.flatMap { it.feelings }
        val totalFeelingsCount = allFeelingsRecorded.size

        if (totalFeelingsCount == 0) {
            return PeriodFeelingSummary(null, emptyList())
        }

        val feelingCounts = allFeelingsRecorded.groupingBy { it }.eachCount()
        val mostFrequent = feelingCounts.maxByOrNull { it.value }?.key

        val feelingPercentages = feelingCounts.map { (feeling, count) ->
            FeelingPercentage(
                feeling = feeling,
                count = count,
                percentage = (count.toFloat() / totalFeelingsCount),
                color = feeling.colorHex
            )
        }

        return PeriodFeelingSummary(
            mostFrequentFeeling = mostFrequent,
            feelingPercentages = feelingPercentages.sortedByDescending { it.percentage }
        )
    }

    private fun calculateWellbeingPanel(results: List<UserTestResult>): WellbeingPanel {
        val anxietyResults = results.filter { it.testId == "1" }.sortedByDescending { it.timestamp }
        val burnoutResults = results.filter { it.testId == "2" }.sortedByDescending { it.timestamp }
        val depressionResults = results.filter { it.testId == "3" }.sortedByDescending { it.timestamp }

        fun getTrend(history: List<UserTestResult>): WellbeingPanel.Trend {
            if (history.size < 2) return WellbeingPanel.Trend.STABLE
            val latest = history[0].score
            val previous = history[1].score
            return when {
                latest > previous -> WellbeingPanel.Trend.UP
                latest < previous -> WellbeingPanel.Trend.DOWN
                else -> WellbeingPanel.Trend.STABLE
            }
        }

        return WellbeingPanel(
            anxietyLevel = anxietyResults.firstOrNull()?.level ?: "N/A",
            burnoutRisk = burnoutResults.firstOrNull()?.level ?: "N/A",
            depressionLevel = depressionResults.firstOrNull()?.level ?: "N/A",
            anxietyTrend = getTrend(anxietyResults),
            burnoutTrend = getTrend(burnoutResults),
            depressionTrend = getTrend(depressionResults)
        )
    }

    /**
     * CORRIGIDO: Prepara os dados extraindo apenas a pontuação (Float).
     */
    private fun calculatePsychosocialTrend(results: List<UserTestResult>): PsychosocialTrend {
        val trendsMap = mutableMapOf<String, List<Float>>()

        // Agrupa resultados, ordena por data e extrai apenas a pontuação como Float
        val anxietyData = results
            .filter { it.testId == "1" }
            .sortedBy { it.timestamp }
            .map { it.score.toFloat() } // <-- CORREÇÃO AQUI

        val burnoutData = results
            .filter { it.testId == "2" }
            .sortedBy { it.timestamp }
            .map { it.score.toFloat() } // <-- CORREÇÃO AQUI

        val depressionData = results
            .filter { it.testId == "3" }
            .sortedBy { it.timestamp }
            .map { it.score.toFloat() } // <-- CORREÇÃO AQUI

        trendsMap["Ansiedade"] = anxietyData
        trendsMap["Burnout"] = burnoutData
        trendsMap["Depressão"] = depressionData

        // Agora o tipo de 'trendsMap' corresponde ao esperado por PsychosocialTrend
        return PsychosocialTrend(trends = trendsMap)
    }


    // --- Funções Auxiliares e de Geração ---

    private fun generateRecommendations(panel: WellbeingPanel): List<Recommendation> {
        val recommendations = mutableListOf<Recommendation>()
        if (panel.burnoutRisk == "Alto") {
            recommendations.add(
                Recommendation("rec1", "Pausa para Respirar", "Seu nível de burnout está alto. Que tal uma pausa de 5 minutos?", "Iniciar Exercício")
            )
        }
        if (panel.anxietyLevel in listOf("Alto", "Moderado")) {
            recommendations.add(
                Recommendation("rec2", "Entenda a Ansiedade", "Leia nosso artigo sobre como lidar com a ansiedade no dia a dia.", "Ler Artigo")
            )
        }
        if (panel.depressionLevel in listOf("Alto", "Moderado")) {
            recommendations.add(
                Recommendation("rec4", "Fale com Alguém", "Considerar conversar com um profissional pode ser um passo importante.", "Buscar Ajuda")
            )
        }
        if (recommendations.isEmpty()) {
            recommendations.add(
                Recommendation("rec3", "Continue Assim!", "Seus indicadores estão ótimos. Continue se cuidando.", "Ver Dicas")
            )
        }
        return recommendations
    }


    private fun generateMockCheckins(days: Int): List<UserCheckin> {
        val checkins = mutableListOf<UserCheckin>()
        for (i in 0 until days) {
            if (Random.nextDouble() < 0.75) {
                val feelings = mockCheckinFeelings.shuffled().take(Random.nextInt(1, 4))
                checkins.add(
                    UserCheckin(
                        id = "checkin_$i",
                        userId = "u1",
                        date = LocalDate.now().minusDays(i.toLong()),
                        feelings = feelings
                    )
                )
            }
        }
        return checkins
    }

    private fun generateMockTestResults(days: Int): List<UserTestResult> {
        val results = mutableListOf<UserTestResult>()
        var currentDate = System.currentTimeMillis()
        val numberOfTests = (days / 10).coerceAtLeast(3)

        for (i in 0..numberOfTests) {
            val test = mockTests.filter { it.id in listOf("1","2","3") }.random()
            results.add(
                UserTestResult(
                    testId = test.id,
                    score = Random.nextInt(5, 25),
                    level = listOf("Baixo", "Moderado", "Alto").random(),
                    interpretation = "Interpretação para o resultado do teste ${test.title}.",
                    recommendations = listOf("Recomendação 1", "Recomendação 2"),
                    timestamp = currentDate
                )
            )
            val daysToSubtract = Random.nextInt(5, 15) * 24 * 60 * 60 * 1000L
            currentDate -= daysToSubtract
        }
        return results.sortedBy { it.timestamp }
    }
}