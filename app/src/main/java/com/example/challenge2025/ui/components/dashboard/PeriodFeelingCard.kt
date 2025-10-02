package com.example.challenge2025.ui.components.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColorInt
import com.example.challenge2025.domain.model.checkin.FeelingsData
import com.example.challenge2025.domain.model.dashboard.FeelingPercentage
import com.example.challenge2025.domain.model.dashboard.PeriodFeelingSummary

@Composable
fun PeriodFeelingCard(summary: PeriodFeelingSummary) {
    var showDetailsDialog by remember { mutableStateOf(false) }

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
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = cardElevation
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Sentimento do Período",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (summary.feelingPercentages.isEmpty()) {
                Text(
                    "Faça check-ins para ver o resumo dos seus sentimentos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeelingDonutChart(summary = summary, size = 140.dp)

                    Column {
                        Text(
                            text = "Sentimento Frequente:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Text(
                            text = summary.mostFrequentFeeling,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(onClick = { showDetailsDialog = true }) {
                            Text("Ver Detalhes")
                        }
                    }
                }
            }
        }
    }

    if (showDetailsDialog) {
        FeelingDetailsDialog(
            percentages = summary.feelingPercentages,
            onDismiss = { showDetailsDialog = false },
            isDarkTheme = isDarkTheme
        )
    }
}

@Composable
fun FeelingDonutChart(summary: PeriodFeelingSummary, size: Dp) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {
        // Busca o objeto 'Feeling' completo para pegar o ícone e a cor
        val frequentFeelingObject = FeelingsData.availableFeelings.find {
            it.name.equals(summary.mostFrequentFeeling, ignoreCase = true)
        }

        if (frequentFeelingObject != null) {
            Image(
                painter = painterResource(id = frequentFeelingObject.iconRes),
                contentDescription = frequentFeelingObject.name,
                modifier = Modifier
                    .size(size / 2.5f)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    Color(frequentFeelingObject.colorHex.toColorInt())
                )
            )
        }

        val chartData = summary.feelingPercentages
        var startAngle = -90f

        chartData.forEach { feelingData ->
            val sweepAngle = feelingData.percentage * 360f
            val color = Color(feelingData.color.toColorInt())
            Canvas(modifier = Modifier.size(size)) {
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 25f, cap = StrokeCap.Butt)
                )
            }
            startAngle += sweepAngle
        }
    }
}

@Composable
fun FeelingDetailsDialog(
    percentages: List<FeelingPercentage>,
    onDismiss: () -> Unit,
    isDarkTheme: Boolean
) {
    val dialogCardColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = dialogCardColor)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Detalhes dos Sentimentos", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(percentages) { data ->
                        val color = Color(data.color.toColorInt())
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(color, shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = data.feelingName,
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${data.count}x (${(data.percentage * 100).toInt()}%)",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Fechar")
                }
            }
        }
    }
}