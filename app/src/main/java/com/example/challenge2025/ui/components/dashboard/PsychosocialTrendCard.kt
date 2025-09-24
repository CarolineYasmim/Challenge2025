package com.example.challenge2025.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.challenge2025.model.dashboard.PsychosocialTrend

@Composable
fun PsychosocialTrendCard(trendData: PsychosocialTrend) {
    val isDarkTheme = isSystemInDarkTheme()

    val cardColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val cardElevation = if (isDarkTheme) {
        CardDefaults.cardElevation(4.dp)
    } else {
        CardDefaults.cardElevation(0.dp)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = cardElevation,
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título: Já segue o padrão (titleLarge, Bold).
            Text(
                text = "Minhas Tendências",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            val trendTypes = listOf("Ansiedade", "Burnout", "Depressão")
            var selectedTabIndex by remember { mutableIntStateOf(0) }

            // Cor de fundo do TabRow adaptada para o tema
            val tabRowContainerColor = if (isDarkTheme) Color.Transparent else MaterialTheme.colorScheme.surface
            val tabContentColor = if (isDarkTheme) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary

            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = tabRowContainerColor,
                contentColor = tabContentColor,
                divider = {}
            ) {
                trendTypes.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            val selectedTrend = trendData.trends[trendTypes[selectedTabIndex]]

            if (selectedTrend.isNullOrEmpty() || selectedTrend.size < 2) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Faça mais testes para ver sua evolução.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            } else {
                // Placeholder para o Gráfico de Linha
                val placeholderBackgroundColor = if (isDarkTheme) {
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            color = placeholderBackgroundColor,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Gráfico de Linha para ${trendTypes[selectedTabIndex]}",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}