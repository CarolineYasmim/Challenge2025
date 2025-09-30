// ARQUIVO NOVO: data/mappers/TestMapper.kt

package com.example.challenge2025.data.mappers

import com.example.challenge2025.R
import com.example.challenge2025.data.remote.dto.test.TentativaResponseDto
import com.example.challenge2025.data.remote.dto.test.TesteResponseDto
import com.example.challenge2025.domain.model.tests.TestCategory
import com.example.challenge2025.domain.model.tests.TestItem
import com.example.challenge2025.domain.model.tests.TestStatus
import java.time.LocalDate

fun TesteResponseDto.toDomainModel(userAttempt: TentativaResponseDto?): TestItem {

    // Lógica para determinar o ícone (pode ser melhorada no futuro)
    val icon = when (this.type.uppercase()) {
        "USER" -> when {
            this.title.contains("Ansiedade", true) -> R.drawable.ansiedade
            this.title.contains("Burnout", true) -> R.drawable.burnout
            this.title.contains("Depressão", true) -> R.drawable.depressao
            else -> R.drawable.defalt_test
        }
        "COMPANY" -> R.drawable.empresa_test
        else -> R.drawable.defalt_test
    }

    // Lógica para determinar o status e a data com base na tentativa do usuário
    val status: TestStatus
    val lastResultDate: LocalDate?

    if (userAttempt != null && userAttempt.status.uppercase() == "CONCLUIDA") {
        status = TestStatus.DONE
        lastResultDate = LocalDate.parse(userAttempt.updateDate.substring(0, 10))
    } else {
        status = TestStatus.TODO
        lastResultDate = null
    }

    return TestItem(
        id = this.id,
        title = this.title,
        description = this.description,
        durationMinutes = this.durationMinutes,
        status = status,
        category = try {
            TestCategory.valueOf(this.type.uppercase())
        } catch (e: IllegalArgumentException) {
            TestCategory.USER // Categoria padrão
        },
        iconRes = icon,
        lastResultDate = lastResultDate
    )
}