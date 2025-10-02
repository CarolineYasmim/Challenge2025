package com.example.challenge2025.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challenge2025.ui.components.dashboard.DashboardHeader
import com.example.challenge2025.ui.components.dashboard.EmotionalJourney
import com.example.challenge2025.ui.components.dashboard.PeriodFeelingCard
import com.example.challenge2025.ui.components.dashboard.PsychosocialTrendCard
import com.example.challenge2025.ui.components.dashboard.Recommendations
import com.example.challenge2025.ui.components.dashboard.RiskProfileCard
import com.example.challenge2025.ui.components.dashboard.WellbeingPanel
import com.example.challenge2025.ui.viewmodel.dashboard.DashboardViewModel

@Composable
fun DashboardScreen(
    // MUDANÇA 1: Usar hiltViewModel() para garantir a injeção correta
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        // Exibe um erro de tela cheia se houver um problema e não tiver dados
        if (uiState.error != null && uiState.dashboardData == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(uiState.error ?: "Ocorreu um erro desconhecido.")
            }
        }
        // Exibe a tela principal, mesmo que esteja carregando novos dados de período
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    DashboardHeader(
                        selectedPeriod = uiState.selectedPeriod,
                        onPeriodChange = viewModel::setPeriod
                    )
                }

                // Mostra um loading geral no topo se estiver buscando dados
                if (uiState.isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                // Mostra os dados se não estiver em estado de erro inicial
                uiState.dashboardData?.let { data ->
                    item {
                        WellbeingPanel(panel = data.wellbeingPanel)
                    }
                    item {
                        PeriodFeelingCard(summary = data.periodFeelingSummary)
                    }
                    item {
                        Recommendations(recommendations = data.recommendations)
                    }
                    item {
                        PsychosocialTrendCard(trendData = data.psychosocialTrend)
                    }
                    item {
                        // MUDANÇA 2: Passar os dados necessários para o EmotionalJourney
                        EmotionalJourney(summary = data.periodFeelingSummary)
                    }
                    item {
                        RiskProfileCard(profile = data.riskProfile)
                    }
                }
            }
        }
    }
}