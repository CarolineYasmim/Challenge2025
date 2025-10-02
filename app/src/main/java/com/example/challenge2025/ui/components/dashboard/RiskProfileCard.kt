package com.example.challenge2025.ui.components.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challenge2025.domain.model.dashboard.RiskProfile
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RiskProfileCard(profile: RiskProfile) {
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
                text = "Meu Perfil de Risco",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (profile.radarChartData.isEmpty()) {
                Text(
                    "Complete seus testes para ver seu perfil de risco.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            } else {
                // MUDANÇA: Substituímos o Box pelo nosso Gráfico de Radar customizado
                RadarChart(
                    data = profile.radarChartData,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp) // Aumentar a altura para o gráfico
                )
            }
        }
    }
}

// MUDANÇA: Novo componente de Gráfico de Radar nativo
@OptIn(ExperimentalTextApi::class)
@Composable
private fun RadarChart(
    data: Map<String, Int>,
    modifier: Modifier = Modifier,
    maxValue: Int = 25 // Pontuação máxima possível em um teste
) {
    val labels = data.keys.toList()
    val values = data.values.toList()
    val numAxes = labels.size
    val angleStep = 360f / numAxes

    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.onSurface
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2.5f

        // 1. Desenha as linhas do eixo (a "teia de aranha")
        for (i in 0 until numAxes) {
            val angle = i * angleStep - 90
            val x = center.x + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = center.y + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
            drawLine(
                color = surfaceColor.copy(alpha = 0.3f),
                start = center,
                end = Offset(x, y)
            )
        }

        // 2. Desenha o polígono com os dados do usuário
        val path = Path()
        values.forEachIndexed { i, value ->
            val ratio = value.toFloat() / maxValue
            val angle = i * angleStep - 90
            val x = center.x + (radius * ratio) * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = center.y + (radius * ratio) * sin(Math.toRadians(angle.toDouble())).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        drawPath(path, color = primaryColor.copy(alpha = 0.3f))
        drawPath(path, color = primaryColor, style = Stroke(width = 4f))

        // 3. Desenha os labels (textos)
        labels.forEachIndexed { i, label ->
            val angle = i * angleStep - 90
            val labelRadius = radius * 1.2f // Posiciona o texto um pouco para fora
            val x = center.x + labelRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = center.y + labelRadius * sin(Math.toRadians(angle.toDouble())).toFloat()

            drawText(
                textMeasurer = textMeasurer,
                text = label,
                style = TextStyle(color = surfaceColor, fontSize = 12.sp),
                topLeft = Offset(x - 30, y - 10) // Ajustes de posição do texto
            )
        }
    }
}