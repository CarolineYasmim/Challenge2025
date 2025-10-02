// ARQUIVO NOVO: ui/components/charts/BarChart.kt

package com.example.challenge2025.ui.components.assets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

data class BarChartData(
    val value: Float,
    val label: String,
    val color: Color
)

@Composable
fun BarChart(
    data: List<BarChartData>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return

    val maxValue = data.maxOfOrNull { it.value } ?: 0f

    Canvas(modifier = modifier.fillMaxSize()) {
        val barWidth = (size.width / data.size) * 0.6f
        val spaceBetweenBars = (size.width / data.size) * 0.4f
        val startX = spaceBetweenBars / 2

        data.forEachIndexed { index, barData ->
            val barHeight = (barData.value / maxValue) * size.height
            val topLeft = Offset(
                x = startX + index * (barWidth + spaceBetweenBars),
                y = size.height - barHeight
            )
            drawRect(
                color = barData.color,
                topLeft = topLeft,
                size = Size(barWidth, barHeight)
            )
        }
    }
}