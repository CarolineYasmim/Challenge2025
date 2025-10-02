package com.example.challenge2025.ui.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.data.mappers.toDomainModel
import com.example.challenge2025.data.remote.dto.checkin.CheckInDiarioRequestDto
import com.example.challenge2025.domain.model.checkin.CalendarWeek
import com.example.challenge2025.domain.model.checkin.Feeling
import com.example.challenge2025.domain.model.checkin.FeelingsData
import com.example.challenge2025.domain.model.checkin.UserCheckin
import com.example.challenge2025.domain.repository.CheckinRepository
import com.example.challenge2025.domain.util.Resource
import java.time.LocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CheckinViewModel @Inject constructor(
    private val repository: CheckinRepository
):ViewModel() {
    private val _selectedFeelings = MutableStateFlow<List<Feeling>>(emptyList())
    val selectedFeelings: StateFlow<List<Feeling>> = _selectedFeelings

    private val _checkinNotes = MutableStateFlow("")
    val checkinNotes: StateFlow<String> = _checkinNotes

    private val _submitState = MutableStateFlow<Resource<Unit>?>(null)
    val submitState: StateFlow<Resource<Unit>?> = _submitState.asStateFlow()

    private val _weekCheckins = MutableStateFlow<Map<LocalDate, UserCheckin>>(emptyMap())
    val weekCheckins: StateFlow<Map<LocalDate, UserCheckin>> = _weekCheckins.asStateFlow()

    val availableFeelings = FeelingsData.availableFeelings

    fun loadCheckinsForWeek(week: CalendarWeek) {
        viewModelScope.launch {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
            val result = repository.getCheckinsForWeek(
                week.startDate.format(formatter),
                week.endDate.format(formatter)
            )

            if (result is Resource.Success) {
                _weekCheckins.value = result.data
                    ?.map { dto -> dto.toDomainModel(availableFeelings) }
                    ?.associateBy { it.date } ?: emptyMap()
            } else {
                _weekCheckins.value = emptyMap()
            }
        }
    }

    fun toggleFeeling(feeling: Feeling) {
        val currentList = _selectedFeelings.value.toMutableList()
        if (currentList.contains(feeling)) {
            currentList.remove(feeling)
        } else {
            currentList.add(feeling)
        }
        _selectedFeelings.value = currentList
    }

    fun updateNotes(notes: String) {
        _checkinNotes.value = notes
    }

    fun submitCheckin() {
        viewModelScope.launch {
            _submitState.value = Resource.Loading()

            val request = CheckInDiarioRequestDto(
                feelingIds = _selectedFeelings.value.map { it.id },
                notes = _checkinNotes.value.ifBlank { null }
            )

            val result = repository.postCheckin(request)


            _submitState.value = when (result) {
                is Resource.Success -> Resource.Success(Unit)
                is Resource.Error -> Resource.Error(result.message)
                is Resource.Loading -> Resource.Loading()
            }


            if (result is Resource.Success) {
                _selectedFeelings.value = emptyList()
                _checkinNotes.value = ""
            }
        }
    }
}