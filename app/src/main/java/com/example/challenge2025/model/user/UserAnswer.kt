package com.example.challenge2025.model.user

data class UserAnswer(
    val testId: String,
    val questionId: String,
    val selectedOption: Int,
    val value: Int
)