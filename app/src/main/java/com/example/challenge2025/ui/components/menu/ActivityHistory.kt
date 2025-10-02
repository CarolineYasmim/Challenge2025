package com.example.challenge2025.ui.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun ActivityHistory(
    // MUDANÇA 1: Receber o mapa de atividades como parâmetro
    activityMap: Map<LocalDate, Int>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Sua Jornada de Bem-Estar",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp, bottom = 16.dp)
        )

        // MUDANÇA 2: Lógica para desenhar o mapa de calor com dados reais
        Heatmap(activityMap = activityMap)
    }
}

@Composable
private fun Heatmap(activityMap: Map<LocalDate, Int>) {
    val endDate = LocalDate.now()
    val startDate = endDate.minusDays(139) // Aproximadamente 20 semanas
    val days = ChronoUnit.DAYS.between(startDate, endDate).toInt() + 1

    val daysOfWeek = 7
    val totalColumns = 20

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(daysOfWeek) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(totalColumns) { colIndex ->
                    val dayIndex = colIndex * daysOfWeek + rowIndex
                    if (dayIndex < days) {
                        val date = startDate.plusDays(dayIndex.toLong())
                        val intensity = activityMap[date] ?: 0

                        // Define a cor com base na intensidade (número de sentimentos)
                        val color = when (intensity) {
                            0 -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f) // Sem check-in
                            1 -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                            2 -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                            else -> MaterialTheme.colorScheme.primary // 3 ou mais
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(color)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f).aspectRatio(1f))
                    }
                }
            }
        }
    }
}