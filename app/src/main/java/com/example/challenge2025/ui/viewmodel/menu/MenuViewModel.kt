package com.example.challenge2025.ui.viewmodel.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.data.mappers.toDomainModel
import com.example.challenge2025.domain.model.checkin.FeelingsData
import com.example.challenge2025.domain.repository.CheckinRepository
import com.example.challenge2025.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


typealias ActivityMap = Map<LocalDate, Int>

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val checkinRepository: CheckinRepository
) : ViewModel() {

    private val _activityHistoryState = MutableStateFlow<Resource<ActivityMap>>(Resource.Loading())
    val activityHistoryState = _activityHistoryState.asStateFlow()

    init {
        loadActivityHistory()
    }

    private fun loadActivityHistory() {
        viewModelScope.launch {
            _activityHistoryState.value = Resource.Loading()


            val endDate = LocalDate.now()
            val startDate = endDate.minusDays(180)
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE

            val result = checkinRepository.getCheckinsForWeek(
                startDate.format(formatter),
                endDate.format(formatter)
            )

            if (result is Resource.Success && result.data != null) {

                val domainCheckins = result.data.map {
                    it.toDomainModel(FeelingsData.availableFeelings)
                }


                val activityMap = domainCheckins.associate {
                    it.date to it.feelings.size.coerceAtLeast(1)
                }

                _activityHistoryState.value = Resource.Success(activityMap)
            } else if (result is Resource.Error) {
                _activityHistoryState.value = Resource.Error(result.message ?: "Erro ao buscar histórico")
            }
        }
    }
}