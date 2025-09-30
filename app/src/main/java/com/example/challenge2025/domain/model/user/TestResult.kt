package com.example.challenge2025.domain.model.user

data class UserTestResult(
    val testId: String,
    val score: Int,
    val level: String,
    val interpretation: String,
    val recommendations: List<String>,
    val timestamp: Long
)
