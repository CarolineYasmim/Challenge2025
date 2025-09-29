package com.example.challenge2025.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.challenge2025.ui.components.dashboard.DashboardHeader
import com.example.challenge2025.ui.components.dashboard.EmotionalJourney
import com.example.challenge2025.ui.components.dashboard.PsychosocialTrendCard
import com.example.challenge2025.ui.components.dashboard.Recommendations
import com.example.challenge2025.ui.components.dashboard.RiskProfileCard
import com.example.challenge2025.ui.components.dashboard.WellbeingPanel
import com.example.challenge2025.ui.components.dashboard.PeriodFeelingCard
import com.example.challenge2025.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            uiState.dashboardData?.let { data ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        DashboardHeader(
                            selectedPeriod = uiState.selectedPeriod,
                            onPeriodChange = { viewModel.setPeriod(it) }
                        )
                    }
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
                        EmotionalJourney()
                    }
                    item {
                        RiskProfileCard(profile = data.riskProfile)
                    }
                }
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Não foi possível carregar os dados do dashboard.")
                }
            }
        }
    }
}