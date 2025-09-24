@file:Suppress("DEPRECATION")

package com.example.challenge2025.ui.components.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challenge2025.model.dashboard.TimePeriod

@Composable
fun DashboardHeader(
    selectedPeriod: TimePeriod,
    onPeriodChange: (TimePeriod) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Meu Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PeriodButton(
                text = "Semanal",
                isSelected = selectedPeriod == TimePeriod.WEEKLY,
                onClick = { onPeriodChange(TimePeriod.WEEKLY) },
                modifier = Modifier.weight(1f)
            )
            PeriodButton(
                text = "Mensal",
                isSelected = selectedPeriod == TimePeriod.MONTHLY,
                onClick = { onPeriodChange(TimePeriod.MONTHLY) },
                modifier = Modifier.weight(1f)
            )
            PeriodButton(
                text = "Anual",
                isSelected = selectedPeriod == TimePeriod.YEARLY,
                onClick = { onPeriodChange(TimePeriod.YEARLY) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PeriodButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = if (isSelected) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        border = if(isSelected) null else ButtonDefaults.outlinedButtonBorder
    ) {
        Text(text = text, fontSize = 12.sp)
    }
}