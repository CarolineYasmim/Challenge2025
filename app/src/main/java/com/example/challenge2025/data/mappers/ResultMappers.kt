// ARQUIVO: data/mappers/ResultMapper.kt

package com.example.challenge2025.data.mappers

import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.domain.model.tests.UserTestResult
import java.text.SimpleDateFormat
import java.util.*

fun ResultadoResponseDto.toDomainModel(): UserTestResult {
    // Tenta converter a data da API para um formato de timestamp (Long)
    val timestamp = try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("UTC")
        parser.parse(this.completionDate)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        System.currentTimeMillis()
    }

    // Lógica para definir a interpretação com base no nível de risco
    val interpretation = when (this.riskLevel.uppercase()) {
        "ALTO" -> "Seu resultado indica um nível elevado. É importante buscar apoio."
        "MODERADO" -> "Seu resultado está em um nível intermediário. Fique atento(a) aos sinais."
        "BAIXO" -> "Seu resultado indica um nível baixo. Continue se cuidando!"
        else -> "Análise do seu resultado."
    }

    // MOCK DAS RECOMENDAÇÕES (como você pediu)
    val recommendations = listOf(
        "Converse com alguém de sua confiança sobre como se sente.",
        "Mantenha hábitos saudáveis, como boa alimentação e sono regular.",
        "Considere buscar apoio de um profissional de saúde mental."
    )

    // Monta o objeto final "limpo"
    return UserTestResult(
        testId = this.testId,
        score = this.totalScore,
        level = this.riskLevel,
        interpretation = interpretation,
        recommendations = recommendations, // <-- Usando a lista mockada
        timestamp = timestamp
    )
}