package com.example.challenge2025.ui.components.dashboard

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.challenge2025.domain.model.dashboard.WellbeingPanel
import com.example.challenge2025.ui.theme.Green
@Composable
fun WellbeingPanel(panel: WellbeingPanel) {
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
            Text(
                text = "Balanço Geral",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Indicator("Ansiedade", panel.anxietyLevel, getTrendIcon(panel.anxietyTrend))
                Indicator("Burnout", panel.burnoutRisk, getTrendIcon(panel.burnoutTrend))
                Indicator("Depressão", panel.depressionLevel, getTrendIcon(panel.depressionTrend))
            }
        }
    }
}

@Composable
private fun RowScope.Indicator(title: String, value: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            if (value != "N/A") {
                Icon(
                    imageVector = icon,
                    contentDescription = "Tendência",
                    modifier = Modifier
                        .size(16.dp)
                        .padding(start = 4.dp),
                    tint = when (icon) {
                        Icons.Default.ArrowUpward -> MaterialTheme.colorScheme.tertiary
                        Icons.Default.ArrowDownward -> Green
                        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    }
                )
            }
        }
    }
}

@Composable
private fun getTrendIcon(trend: WellbeingPanel.Trend): ImageVector {
    return when (trend) {
        WellbeingPanel.Trend.UP -> Icons.Default.ArrowUpward
        WellbeingPanel.Trend.DOWN -> Icons.Default.ArrowDownward
        WellbeingPanel.Trend.STABLE -> Icons.AutoMirrored.Filled.ArrowForward
    }
}