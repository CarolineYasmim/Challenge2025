// UI/Components/WeeklyCalendar.kt
@file:Suppress("DEPRECATION")

package com.example.challenge2025.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challenge2025.model.dailyCheckin.CalendarDay
import com.example.challenge2025.model.dailyCheckin.CalendarWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WeeklyCalendar(
    week: CalendarWeek,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        // 1ª Linha: Data formatada (ex: "22 de agosto, 2025")
        val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy", Locale("pt", "BR"))
        Text(
            text = selectedDate.format(formatter),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        // 2ª Linha: Dias da semana
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            week.days.forEach { day ->
                DayCell(
                    day = day,
                    isSelected = day.date == selectedDate,
                    onDateSelected = onDateSelected
                )
            }
        }
    }
}


@Composable
fun DayCell(
    day: CalendarDay,
    isSelected: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onDateSelected(day.date) }
            .padding(4.dp)
    ) {
        // Container redondo para o número do dia
        Surface(
            shape = CircleShape,
            color = when {
                isSelected -> MaterialTheme.colorScheme.primary
                day.isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            },
            border = when {
                day.isToday -> BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                else -> null
            },
            modifier = Modifier.size(40.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.dayOfMonth.toString().padStart(2, '0'),
                    color = when {
                        isSelected -> MaterialTheme.colorScheme.onPrimary
                        day.isToday -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Abreviação do dia abaixo do número
        Text(
            text = day.dayAbbreviation,
            color = when {
                isSelected -> MaterialTheme.colorScheme.primary
                day.isToday -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            },
            fontSize = 12.sp,
            fontWeight = if (day.isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(top = 4.dp)
        )

        // Se for hoje, mostra a label "Hoje"
        if (day.isToday) {
            Text(
                text = "Hoje",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}