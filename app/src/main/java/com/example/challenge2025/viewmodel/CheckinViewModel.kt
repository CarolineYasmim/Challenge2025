// ViewModel/CheckinViewModel.kt
package com.example.challenge2025.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.model.checkin.Checkin
import com.example.challenge2025.model.user.Feeling
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CheckinViewModel : ViewModel() {
    private val _selectedFeelings = MutableStateFlow<List<Feeling>>(emptyList())
    val selectedFeelings: StateFlow<List<Feeling>> = _selectedFeelings

    private val _checkinNotes = MutableStateFlow("")
    val checkinNotes: StateFlow<String> = _checkinNotes

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

    fun submitCheckin(date: LocalDate) {
        viewModelScope.launch {
            Checkin.submitCheckin(
                date = date,
                selectedFeelings = _selectedFeelings.value,
                notes = _checkinNotes.value.ifEmpty { null }
            )
            // Limpa o estado após submissão
            _selectedFeelings.value = emptyList()
            _checkinNotes.value = ""
        }
    }

    fun clearSelection() {
        _selectedFeelings.value = emptyList()
        _checkinNotes.value = ""
    }
}