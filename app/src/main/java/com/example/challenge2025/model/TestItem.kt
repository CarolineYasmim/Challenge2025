package com.example.challenge2025.model

data class TestItem(
    val id: String,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val status: TestStatus,
    val category: TestCategory,
    val iconRes: Int
)

enum class TestStatus{
    TODO,
    DONE
}

enum class TestCategory{
    USER,
    COMPANY
}

