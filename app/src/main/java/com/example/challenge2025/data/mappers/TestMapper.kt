// ARQUIVO NOVO: data/mappers/TestMapper.kt

package com.example.challenge2025.data.mappers

import com.example.challenge2025.R
import com.example.challenge2025.data.remote.dto.test.TentativaResponseDto
import com.example.challenge2025.data.remote.dto.test.TesteResponseDto
import com.example.challenge2025.domain.model.tests.TestItem
import com.example.challenge2025.domain.model.tests.TestStatus
import java.time.LocalDate

fun TesteResponseDto.toDomainModel(userAttempt: TentativaResponseDto?): TestItem {

    val icon = when (this.type.uppercase()) {
        "ANSIEDADE" -> R.drawable.ansiedade
        "BURNOUT" -> R.drawable.burnout
        "DEPRESSÃO" -> R.drawable.depressao
        else -> R.drawable.defalt_test
    }

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
        category = this.type,
        iconRes = icon,
        lastResultDate = lastResultDate
    )
}