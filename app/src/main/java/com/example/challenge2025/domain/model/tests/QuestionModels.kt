// ARQUIVO: domain/model/tests/QuestionModels.kt

package com.example.challenge2025.domain.model.tests

// Este será o nosso modelo "limpo" para uma pergunta e suas opções,
// que usaremos dentro do ViewModel e da UI.
data class Question(
    val id: String, // ID da pergunta, vindo da API
    val text: String,
    val options: List<AnswerOption>
)

data class AnswerOption(
    val text: String,
    val value: Int
)