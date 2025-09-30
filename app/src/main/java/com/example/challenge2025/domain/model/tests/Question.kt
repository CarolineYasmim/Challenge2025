package com.example.challenge2025.domain.model.tests

data class Question(
    val id: String,
    val testId: String,
    val text: String,
    val options:List<AnswerOption>
)