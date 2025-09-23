// ViewModel/CalendarViewModel.kt
package com.example.challenge2025.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.model.checkin.CalendarDay
import com.example.challenge2025.model.checkin.CalendarWeek
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Suppress("DEPRECATION")
class CalendarViewModel : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _currentWeek = MutableStateFlow(buildWeekForDate(LocalDate.now()))
    val currentWeek: StateFlow<CalendarWeek> = _currentWeek.asStateFlow()

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        // Se a data selecionada não estiver na semana atual, atualiza a semana
        if (date < _currentWeek.value.startDate || date > _currentWeek.value.endDate) {
            _currentWeek.value = buildWeekForDate(date)
        }
    }

    fun setWeekStartingFrom(date: LocalDate) {
        _currentWeek.value = buildWeekForDate(date)
    }

    private fun buildWeekForDate(date: LocalDate): CalendarWeek {
        val startOfWeek = date.with(java.time.DayOfWeek.MONDAY)
        val days = (0 until 7).map { offset ->
            val currentDate = startOfWeek.plusDays(offset.toLong())
            CalendarDay(
                date = currentDate,
                dayOfMonth = currentDate.dayOfMonth,
                dayAbbreviation = currentDate.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale("pt", "BR")
                ).first().toString(),
                isToday = currentDate == LocalDate.now(),
                isSelected = currentDate == _selectedDate.value
            )
        }
        return CalendarWeek(
            startDate = startOfWeek,
            endDate = startOfWeek.plusDays(6),
            days = days
        )
    }

    init {
        viewModelScope.launch {
            // Inicializa com a semana atual
            _currentWeek.value = buildWeekForDate(LocalDate.now())
        }
    }
}