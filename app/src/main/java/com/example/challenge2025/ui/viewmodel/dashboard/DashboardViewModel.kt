package com.example.challenge2025.ui.viewmodel.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.data.mappers.toPeriodFeelingSummaryModel
import com.example.challenge2025.data.mappers.toPsychosocialTrendModel
import com.example.challenge2025.data.mappers.toRiskProfileModel
import com.example.challenge2025.data.mappers.toWellbeingPanelModel
import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.domain.model.dashboard.DashboardData
import com.example.challenge2025.domain.model.dashboard.Recommendation
import com.example.challenge2025.domain.model.dashboard.TimePeriod
import com.example.challenge2025.domain.repository.DashboardRepository
import com.example.challenge2025.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

// Estado que a UI vai observar. Contém tudo que a tela precisa.
data class DashboardUiState(
    val selectedPeriod: TimePeriod = TimePeriod.WEEKLY,
    val dashboardData: DashboardData? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    // Guarda a lista completa de resultados de testes para não ter que buscar toda hora
    private var allTestResults: List<ResultadoResponseDto> = emptyList()

    init {
        // Busca todos os dados necessários quando o ViewModel é criado
        fetchAllInitialData()
    }

    // Função chamada pela UI para trocar o período (Semanal, Mensal, Anual)
    fun setPeriod(period: TimePeriod) {
        if (period != _uiState.value.selectedPeriod) {
            // Apenas processa os dados que já temos, sem nova chamada de API para os testes
            processDataForPeriod(period)
        }
    }

    // Busca os dados que não dependem de período (no caso, o histórico completo de testes)
    private fun fetchAllInitialData() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val testResultsResult = dashboardRepository.getTestResults()

            if (testResultsResult is Resource.Success && testResultsResult.data != null) {
                // Guarda a lista completa na memória do ViewModel
                allTestResults = testResultsResult.data
                // Processa os dados para o período inicial (Semanal)
                processDataForPeriod(TimePeriod.WEEKLY)
            } else {
                _uiState.update { it.copy(isLoading = false, error = testResultsResult.message ?: "Falha ao buscar resultados dos testes.") }
            }
        }
    }

    // Processa os dados para o período selecionado
    private fun processDataForPeriod(period: TimePeriod) {
        _uiState.update { it.copy(isLoading = true, selectedPeriod = period) }
        viewModelScope.launch {
            val days = when (period) {
                TimePeriod.WEEKLY -> 7
                TimePeriod.MONTHLY -> 30
                TimePeriod.YEARLY -> 365
            }

            // --- Filtro dos Testes no Front-end ---
            val now = ZonedDateTime.now()
            val startDate = now.minusDays(days.toLong())
            val filteredTestResults = allTestResults.filter {
                try { ZonedDateTime.parse(it.completionDate).isAfter(startDate) }
                catch (e: Exception) { false }
            }

            // A busca de estatísticas de check-in é feita na API com o filtro de dias
            val statisticsResult = dashboardRepository.getStatistics(days)

            if (statisticsResult is Resource.Success && statisticsResult.data != null) {
                // Usa os mappers para traduzir e montar o objeto final
                val dashboardData = DashboardData(
                    wellbeingPanel = filteredTestResults.toWellbeingPanelModel(),
                    periodFeelingSummary = statisticsResult.data.toPeriodFeelingSummaryModel(),
                    psychosocialTrend = filteredTestResults.toPsychosocialTrendModel(),
                    riskProfile = filteredTestResults.toRiskProfileModel(),
                    recommendations = generateMockRecommendations(filteredTestResults)
                )
                _uiState.update { it.copy(dashboardData = dashboardData, isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = statisticsResult.message ?: "Falha ao buscar dados de estatísticas.") }
            }
        }
    }

    // Manteremos as recomendações mockadas por enquanto
    private fun generateMockRecommendations(results: List<ResultadoResponseDto>): List<Recommendation> {
        val latestBurnout = results.filter { it.testType.equals("BURNOUT", true) }.maxByOrNull { it.completionDate }
        if (latestBurnout?.riskLevel?.equals("Alto", true) == true) {
            return listOf(Recommendation("rec1", "Pausa para Respirar", "Seu nível de burnout está alto.", "Iniciar Exercício"))
        }
        return listOf(Recommendation("rec3", "Continue Assim!", "Seus indicadores estão ótimos.", "Ver Dicas"))
    }
}