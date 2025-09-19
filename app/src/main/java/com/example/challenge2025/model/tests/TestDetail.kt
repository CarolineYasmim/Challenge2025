package com.example.challenge2025.model.tests

import com.example.challenge2025.model.data.MockData

data class TestDetail(
    val testId: String,
    val imageRes: Int,
    val name: String,
    val description: String,
    val totalQuestions: Int,
    val durationMinutes: Int
) {
    companion object {
        fun fromTestId(testId: String): TestDetail? {
            val test = MockData.getTestById(testId) ?: return null
            val totalQuestions = MockData.getQuestionsForTest(testId).size
            val description = MockData.detailedDescriptions[testId] ?: test.description

            return TestDetail(
                testId = test.id,
                imageRes = test.iconRes,
                name = test.title,
                description = description,
                totalQuestions = totalQuestions,
                durationMinutes = test.durationMinutes
            )
        }
    }
}