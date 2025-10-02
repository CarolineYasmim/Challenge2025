package com.example.challenge2025.domain.model.tests

import java.time.LocalDate

data class TestItem(
    val id: String,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val status: TestStatus,
    val category: String,
    val iconRes: Int,
    val lastResultDate: LocalDate? = null
)

enum class TestStatus{
    TODO,
    DONE
}


