package com.example.challenge2025.ui.components.dashboard

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.challenge2025.domain.model.dashboard.PeriodFeelingSummary
import com.example.challenge2025.ui.components.assets.BarChart
import com.example.challenge2025.ui.components.assets.BarChartData

@Composable
fun EmotionalJourney(summary: PeriodFeelingSummary) { // MUDANÇA 1: Receber os dados
    val isDarkTheme = isSystemInDarkTheme()
    val cardColor = if (isDarkTheme) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface
    val cardElevation = if (isDarkTheme) CardDefaults.cardElevation(4.dp) else CardDefaults.cardElevation(0.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = cardElevation,
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Jornada Emocional",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // MUDANÇA 2: Usar o gráfico real em vez do placeholder
            if (summary.feelingPercentages.isEmpty()) {
                Text(
                    "Seus check-ins aparecerão aqui.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            } else {
                // Prepara os dados para o BarChart
                val chartData = summary.feelingPercentages.map {
                    BarChartData(
                        value = it.count.toFloat(),
                        label = it.feelingName,
                        color = Color(it.color.toColorInt())
                    )
                }
                BarChart(
                    data = chartData,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
            }
        }
    }
}