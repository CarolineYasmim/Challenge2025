package com.example.challenge2025.data.mappers

import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.domain.model.tests.UserTestResult
import java.text.SimpleDateFormat
import java.util.*

fun ResultadoResponseDto.toDomainModel(): UserTestResult {
    return UserTestResult(
        testId = this.testId,
        score = this.totalScore,
        level = this.riskLevel,
        interpretation = when (this.riskLevel.lowercase()) {
            "alto" -> "Seu resultado indica um nível elevado. É importante buscar apoio."
            "moderado" -> "Seu resultado está em nível intermediário. Fique atento aos sinais."
            else -> "Seu resultado indica baixo risco. Continue cuidando do seu bem-estar!"
        },
        recommendations = listOf(
            "Converse com alguém de confiança.",
            "Procure manter hábitos saudáveis.",
            "Se sentir necessidade, busque apoio profissional."
        ),
        timestamp = try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            parser.parse(this.completionDate)?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    )
}
