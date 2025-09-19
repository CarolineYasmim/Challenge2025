package com.example.challenge2025.model.dailyCheckin

import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate,
    val dayOfMonth: Int,
    val dayAbbreviation: String,
    val isToday: Boolean,
    val isSelected: Boolean
)

data class CalendarWeek(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val days: List<CalendarDay>
)