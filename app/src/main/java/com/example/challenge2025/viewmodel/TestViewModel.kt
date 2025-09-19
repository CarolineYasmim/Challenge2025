package com.example.challenge2025.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.model.data.MockData
import com.example.challenge2025.model.tests.TestStatus
import com.example.challenge2025.model.user.UserAnswer
import com.example.challenge2025.model.user.UserTestResult
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {

    private val _answers = mutableListOf<UserAnswer>()

    var lastResult: UserTestResult? = null
        private set

    fun addAnswer(answer: UserAnswer) {
        _answers.add(answer)
    }

    fun submitTest(onResult: (UserTestResult) -> Unit) {
        viewModelScope.launch {
            val result = mockBackendCalculation(_answers)
            lastResult = result
            onResult(result)
        }
    }

    private fun mockBackendCalculation(answers: List<UserAnswer>): UserTestResult {
        val score = answers.sumOf { it.value }

        val level = when {
            score < 5 -> "Baixo"
            score < 10 -> "Moderado"
            else -> "Alto"
        }

        val interpretation = when(level) {
            "Baixo" -> "Seu resultado indica que os sinais de depressão/ansiedade estão baixos."
            "Moderado" -> "Seu resultado indica sinais moderados de depressão/ansiedade."
            "Alto" -> "Seu resultado indica sinais altos de depressão/ansiedade. Considere buscar apoio profissional."
            else -> "Resultado indisponível."
        }

        val recommendations = when(level) {
            "Baixo" -> listOf(
                "Continue mantendo hábitos saudáveis.",
                "Pratique atividades de autocuidado regularmente."
            )
            "Moderado" -> listOf(
                "Converse com amigos ou familiares sobre como se sente.",
                "Experimente técnicas de relaxamento ou meditação.",
                "Se sentir necessidade, busque acompanhamento profissional."
            )
            "Alto" -> listOf(
                "Procure imediatamente um profissional de saúde mental qualificado.",
                "Use recursos de apoio e linhas de ajuda 24h.",
                "Pratique exercícios de autocuidado diariamente."
            )
            else -> emptyList()
        }

        return UserTestResult(
            testId = answers.firstOrNull()?.testId ?: "",
            score = score,
            level = level,
            interpretation = interpretation,
            recommendations = recommendations,
            timestamp = System.currentTimeMillis()
        )
    }


    fun markTestAsDone(testId: String) {
        // Atualiza localmente no MockData (ou futuramente envia para backend)
        MockData.updateTestStatus(testId, TestStatus.DONE)
    }

}
