package com.example.challenge2025.ui.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.data.mappers.toDomainModel
import com.example.challenge2025.domain.model.checkin.CalendarDay
import com.example.challenge2025.domain.model.checkin.CalendarWeek
import com.example.challenge2025.domain.model.checkin.FeelingsData
import com.example.challenge2025.domain.model.checkin.UserCheckin
import com.example.challenge2025.domain.model.statistics.StatisticsSummary
import com.example.challenge2025.domain.repository.CheckinRepository
import com.example.challenge2025.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val checkinRepository: CheckinRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _currentWeek = MutableStateFlow(buildWeekForDate(LocalDate.now()))
    val currentWeek: StateFlow<CalendarWeek> = _currentWeek.asStateFlow()

    private val _weekCheckins = MutableStateFlow<Map<LocalDate, UserCheckin>>(emptyMap())
    val weekCheckins: StateFlow<Map<LocalDate, UserCheckin>> = _weekCheckins.asStateFlow()

    private val _statistics = MutableStateFlow<Resource<StatisticsSummary?>>(Resource.Loading())
    val statistics: StateFlow<Resource<StatisticsSummary?>> = _statistics.asStateFlow()

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        if (date < _currentWeek.value.startDate || date > _currentWeek.value.endDate) {
            _currentWeek.value = buildWeekForDate(date)
        } else {
            _currentWeek.value = _currentWeek.value.copy(
                days = _currentWeek.value.days.map { it.copy(isSelected = it.date == date) }
            )
        }
    }

    fun loadCheckinsForWeek(week: CalendarWeek) {
        viewModelScope.launch {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
            val result = checkinRepository.getCheckinsForWeek(
                week.startDate.format(formatter),
                week.endDate.format(formatter)
            )

            if (result is Resource.Success) {
                _weekCheckins.value = result.data
                    ?.map { dto -> dto.toDomainModel(FeelingsData.availableFeelings) }
                    ?.associateBy { it.date } ?: emptyMap()
            } else {
                _weekCheckins.value = emptyMap()
            }
        }
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            _statistics.value = Resource.Loading()


            val result = checkinRepository.getCheckinStatistics(7)

            if (result is Resource.Success && result.data != null) {
                _statistics.value = Resource.Success(result.data.toDomainModel())
            } else if (result is Resource.Error) {
                _statistics.value = Resource.Error(result.message ?: "Erro desconhecido")
            }
        }
    }


    private fun buildWeekForDate(date: LocalDate): CalendarWeek {
        val startOfWeek = date.with(DayOfWeek.MONDAY)
        val days = (0 until 7).map { offset ->
            val currentDate = startOfWeek.plusDays(offset.toLong())
            CalendarDay(
                date = currentDate,
                dayOfMonth = currentDate.dayOfMonth,
                dayAbbreviation = currentDate.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.forLanguageTag("pt-BR")
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
            _currentWeek.collect { week ->
                loadCheckinsForWeek(week)
            }
        }
        loadStatistics()
    }
}
