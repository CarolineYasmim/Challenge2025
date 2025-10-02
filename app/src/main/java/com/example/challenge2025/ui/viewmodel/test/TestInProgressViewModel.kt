package com.example.challenge2025.ui.viewmodel.test

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.data.mappers.toDomainModel
import com.example.challenge2025.data.remote.dto.test.RespostaUsuarioRequestDto
import com.example.challenge2025.data.remote.dto.test.ResultadoRequestDto
import com.example.challenge2025.domain.model.tests.Question
import com.example.challenge2025.domain.model.tests.TestDetail
import com.example.challenge2025.domain.model.tests.UserTestResult
import com.example.challenge2025.domain.repository.ResultRepository
import com.example.challenge2025.domain.repository.TestRepository
import com.example.challenge2025.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestInProgressViewModel @Inject constructor(
    private val testRepository: TestRepository,
    private val resultRepository: ResultRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val testId: String = checkNotNull(savedStateHandle["testId"])

    private val _testState = MutableStateFlow<Resource<TestDetail>>(Resource.Loading())
    val testState = _testState.asStateFlow()

    private val userAnswersList = mutableListOf<RespostaUsuarioRequestDto>()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    private val _finalResultState = MutableStateFlow<Resource<UserTestResult>>(Resource.Loading())
    val finalResultState = _finalResultState.asStateFlow()

    init {
        loadTestQuestions()
    }

    private fun loadTestQuestions() {
        viewModelScope.launch {
            _testState.value = testRepository.getTestDetails(testId)
        }
    }

    fun selectAnswer(question: Question, answerText: String, answerValue: Int) {
        val newAnswer = RespostaUsuarioRequestDto(
            questionId = question.id,
            questionText = question.text,
            selectedAnswerText = answerText,
            value = answerValue
        )
        userAnswersList.add(newAnswer)

        val currentTest = _testState.value.data
        if (currentTest != null) {
            if (_currentQuestionIndex.value < currentTest.totalQuestions - 1) {
                _currentQuestionIndex.value++
            } else {
                submitTestResult()
            }
        }
    }

    private fun submitTestResult() {
        viewModelScope.launch {
            _finalResultState.value = Resource.Loading()
            val request = ResultadoRequestDto(
                testId = testId,
                answers = userAnswersList
            )
            val result = resultRepository.submitTestResult(request)

            if (result is Resource.Success && result.data != null) {
                _finalResultState.value = Resource.Success(result.data.toDomainModel())
            } else if (result is Resource.Error) {
                _finalResultState.value = Resource.Error(result.message ?: "Erro desconhecido")
            }
        }
    }
}