package com.example.challenge2025.domain.model.tests

data class TestDetail(
    val testId: String,
    val imageRes: Int,
    val name: String,
    val description: String,
    val totalQuestions: Int,
    val durationMinutes: Int
)