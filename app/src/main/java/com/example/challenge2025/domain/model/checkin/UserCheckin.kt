// Model/DailyCheckin/UserCheckin.kt
package com.example.challenge2025.domain.model.checkin

import java.time.LocalDate

data class UserCheckin(
    val id: String,
    val userId: String,
    val date: LocalDate,
    val feelings: List<Feeling>,
    val notes: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class Feeling(
    val id: String,
    val name: String,
    val colorHex: String, // Cor em hexadecimal
    val iconRes: Int, // Resource do ícone
    val intensity: Int = 1 // 1-5 escala de intensidade
)

// Estados possíveis para o check-in diário
enum class CheckinStatus {
    NOT_DONE, // Usuário não fez check-in
    COMPLETED // Usuário completou o check-in
}