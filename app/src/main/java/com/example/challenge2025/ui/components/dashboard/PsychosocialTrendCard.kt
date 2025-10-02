package com.example.challenge2025.ui.components.dashboard

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.challenge2025.domain.model.dashboard.PsychosocialTrend

@Composable
fun PsychosocialTrendCard(trendData: PsychosocialTrend) {
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Minhas Tendências",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            val trendTypes = trendData.trends.keys.toList()
            var selectedTabIndex by remember { mutableIntStateOf(0) }

            if (trendTypes.isNotEmpty()) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = if (isDarkTheme) Color.Transparent else MaterialTheme.colorScheme.surface,
                    contentColor = if (isDarkTheme) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                    divider = {}
                ) {
                    trendTypes.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title, fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            val selectedTrend = trendData.trends[trendTypes.getOrNull(selectedTabIndex)]

            if (selectedTrend.isNullOrEmpty() || selectedTrend.size < 2) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Faça mais testes para ver sua evolução.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            } else {
                // MUDANÇA: Substituímos o placeholder por nosso gráfico customizado
                LineChart(
                    data = selectedTrend,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
            }
        }
    }
}

// MUDANÇA: Novo componente de gráfico de linha nativo
@Composable
private fun LineChart(
    data: List<Int>,
    modifier: Modifier = Modifier
) {
    val lineColor = MaterialTheme.colorScheme.primary
    val gradientColor = lineColor.copy(alpha = 0.2f)

    Canvas(modifier = modifier) {
        val (minY, maxY) = (data.minOrNull() ?: 0) to (data.maxOrNull() ?: 0)
        val yRange = (maxY - minY).toFloat().coerceAtLeast(1f)
        val xStep = size.width / (data.size - 1).toFloat()

        val points = data.mapIndexed { index, value ->
            val x = index * xStep
            val y = size.height - ((value - minY) / yRange) * size.height
            Offset(x, y)
        }

        // Desenha a área preenchida com gradiente
        val path = Path().apply {
            moveTo(0f, size.height)
            points.forEach { lineTo(it.x, it.y) }
            lineTo(size.width, size.height)
            close()
        }
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(gradientColor, Color.Transparent)
            )
        )

        // Desenha a linha
        for (i in 0 until points.size - 1) {
            drawLine(
                color = lineColor,
                start = points[i],
                end = points[i + 1],
                strokeWidth = 5f,
                cap = StrokeCap.Round
            )
        }

        // Desenha os pontos
        points.forEach {
            drawCircle(
                color = lineColor,
                radius = 8f,
                center = it
            )
        }
    }
}