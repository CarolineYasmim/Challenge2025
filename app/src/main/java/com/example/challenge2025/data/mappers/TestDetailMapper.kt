// ARQUIVO MODIFICADO: data/mappers/TestDetailMapper.kt

package com.example.challenge2025.data.mappers

import com.example.challenge2025.R
import com.example.challenge2025.data.remote.dto.test.TesteResponseDto
import com.example.challenge2025.domain.model.tests.AnswerOption
import com.example.challenge2025.domain.model.tests.Question
import com.example.challenge2025.domain.model.tests.TestDetail

fun TesteResponseDto.toTestDetailModel(): TestDetail {
    val icon = when (this.type.uppercase()) {
        "ANSIEDADE" -> R.drawable.ansiedade
        "BURNOUT" -> R.drawable.burnout
        "DEPRESSÃO" -> R.drawable.depressao
        else -> R.drawable.defalt_test
    }

    return TestDetail(
        testId = this.id,
        name = this.title,
        description = this.description,
        totalQuestions = this.questions.size,
        durationMinutes = this.durationMinutes,
        imageRes = icon,
        // MUDANÇA CRÍTICA: Mapear as perguntas e opções do DTO para o modelo de domínio
        questions = this.questions.map { perguntaDto ->
            Question(
                id = perguntaDto.id ?: "", // Assumindo que o backend enviará um ID para a pergunta
                text = perguntaDto.text,
                options = perguntaDto.options.map { optionDto ->
                    AnswerOption(
                        text = optionDto.text,
                        value = optionDto.value
                    )
                }
            )
        }
    )
}