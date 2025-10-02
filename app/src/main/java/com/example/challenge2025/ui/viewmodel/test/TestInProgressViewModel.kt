// ARQUIVO NOVO: ui/viewmodel/test/TestInProgressViewModel.kt

package com.example.challenge2025.ui.viewmodel.test

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.data.remote.dto.test.RespostaUsuarioRequestDto
import com.example.challenge2025.data.remote.dto.test.ResultadoRequestDto
import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.domain.model.tests.Question
import com.example.challenge2025.domain.model.tests.TestDetail
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

    // Pega o 'testId' que foi passado como argumento na navegação
    private val testId: String = checkNotNull(savedStateHandle["testId"])

    // Estado para guardar os detalhes do teste (incluindo a lista de perguntas)
    private val _testState = MutableStateFlow<Resource<TestDetail>>(Resource.Loading())
    val testState = _testState.asStateFlow()

    // Lista interna para guardar as respostas que o usuário já deu
    private val userAnswersList = mutableListOf<RespostaUsuarioRequestDto>()

    // Estado para controlar o índice da pergunta atual
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    // Estado para controlar o ENVIO do resultado final
    private val _submitResultState = MutableStateFlow<Resource<ResultadoResponseDto>?>(null)
    val submitResultState = _submitResultState.asStateFlow()

    init {
        loadTestQuestions()
    }

    private fun loadTestQuestions() {
        viewModelScope.launch {
            _testState.value = testRepository.getTestDetails(testId)
        }
    }

    // Função chamada pela UI quando o usuário seleciona uma resposta
    fun selectAnswer(question: Question, answerText: String, answerValue: Int) {
        // Guarda a resposta do usuário no formato que a API de Resultados espera
        val newAnswer = RespostaUsuarioRequestDto(
            questionId = question.id,
            questionText = question.text,
            selectedAnswerText = answerText,
            value = answerValue
        )
        userAnswersList.add(newAnswer)

        // Avança para a próxima pergunta ou finaliza o teste
        val currentTest = _testState.value.data
        if (currentTest != null) {
            if (_currentQuestionIndex.value < currentTest.totalQuestions - 1) {
                _currentQuestionIndex.value++
            } else {
                // Chegou na última pergunta, chama a função para enviar o resultado
                submitTestResult()
            }
        }
    }

    // Função para enviar o resultado final para o backend
    private fun submitTestResult() {
        viewModelScope.launch {
            _submitResultState.value = Resource.Loading()
            val request = ResultadoRequestDto(
                testId = testId,
                answers = userAnswersList
            )
            // Chama a função que criamos no repositório
            _submitResultState.value = resultRepository.submitTestResult(request)
        }
    }
}