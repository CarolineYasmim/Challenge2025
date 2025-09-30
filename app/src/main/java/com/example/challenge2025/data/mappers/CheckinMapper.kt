// ARQUIVO NOVO: data/mappers/CheckinMapper.kt
package com.example.challenge2025.data.mappers

import com.example.challenge2025.data.remote.dto.checkin.CheckInDiarioResponseDto
import com.example.challenge2025.domain.model.checkin.Feeling
import com.example.challenge2025.domain.model.checkin.UserCheckin
import java.time.LocalDate

fun CheckInDiarioResponseDto.toDomainModel(availableFeelings: List<Feeling>): UserCheckin {
    val feelingIdsFromApi = this.feelings.map { it.toString() }
    val matchedFeelings = availableFeelings.filter { feeling ->
        feeling.id in feelingIdsFromApi
    }

    return UserCheckin(
        id = this.id,
        userId = this.userId,
        date = LocalDate.parse(this.dateCheckin),
        feelings = matchedFeelings,
        notes = this.notes
    )
}