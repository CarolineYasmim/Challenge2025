// ARQUIVO NOVO: ui/viewmodel/test/TestDetailViewModel.kt

package com.example.challenge2025.ui.viewmodel.test

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.domain.model.tests.TestDetail
import com.example.challenge2025.domain.repository.TestAttemptRepository
import com.example.challenge2025.domain.repository.TestRepository
import com.example.challenge2025.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestDetailViewModel @Inject constructor(
    private val testRepository: TestRepository,
    private val testAttemptRepository: TestAttemptRepository,
    savedStateHandle: SavedStateHandle // Objeto especial do Hilt para pegar parâmetros da navegação
) : ViewModel() {

    // Estado para guardar os detalhes do teste (Nome, Descrição, etc.)
    private val _testDetailState = MutableStateFlow<Resource<TestDetail>>(Resource.Loading())
    val testDetailState = _testDetailState.asStateFlow()

    // Estado para controlar a ação de "iniciar tentativa" e guardar o ID da tentativa criada
    private val _startAttemptState = MutableStateFlow<Resource<String>?>(null)
    val startAttemptState = _startAttemptState.asStateFlow()

    // Pega o 'testId' que foi passado como argumento na navegação (ex: "testDescription/{testId}")
    private val testId: String = checkNotNull(savedStateHandle["testId"])

    init {
        loadTestDetails()
    }

    private fun loadTestDetails() {
        viewModelScope.launch {
            _testDetailState.value = Resource.Loading()
            _testDetailState.value = testRepository.getTestDetails(testId)
        }
    }

    fun startTestAttempt() {
        viewModelScope.launch {
            _startAttemptState.value = Resource.Loading()
            val result = testAttemptRepository.startAttempt(testId)

            if (result is Resource.Success && result.data != null) {
                // Se deu certo, guardamos o ID da nova tentativa criada
                _startAttemptState.value = Resource.Success(result.data.id)
            } else if (result is Resource.Error) {
                _startAttemptState.value = Resource.Error(result.message ?: "Erro desconhecido ao iniciar tentativa.")
            }
        }
    }
}