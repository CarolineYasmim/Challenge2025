package com.example.challenge2025.ui.viewmodel.test

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.challenge2025.data.mappers.toDomainModel
import com.example.challenge2025.domain.model.tests.UserTestResult
import com.example.challenge2025.domain.util.Resource
import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TestResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _resultState = MutableStateFlow<Resource<UserTestResult>>(Resource.Loading())
    val resultState = _resultState.asStateFlow()

    init {
        val rawResult = savedStateHandle.get<ResultadoResponseDto>("testResult")
        if (rawResult != null) {
            _resultState.value = Resource.Success(rawResult.toDomainModel())
        } else {
            _resultState.value = Resource.Error("Nenhum resultado recebido")
        }
    }
}
