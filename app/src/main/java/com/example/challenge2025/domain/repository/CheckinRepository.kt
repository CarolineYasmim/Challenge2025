// ARQUIVO MODIFICADO: domain/repository/CheckinRepository.kt

package com.example.challenge2025.domain.repository

import com.example.challenge2025.data.remote.dto.checkin.CheckInDiarioRequestDto
import com.example.challenge2025.data.remote.dto.checkin.CheckInDiarioResponseDto // Adicione este import
import com.example.challenge2025.data.remote.dto.sentimentos.EstatisticaSentimentoResponseDto
import com.example.challenge2025.domain.util.Resource

interface CheckinRepository {
    suspend fun postCheckin(checkinData: CheckInDiarioRequestDto): Resource<CheckInDiarioResponseDto>
    suspend fun getCheckinsForWeek(startDate: String, endDate: String): Resource<List<CheckInDiarioResponseDto>>

    suspend fun getCheckinStatistics(days: Int): Resource<EstatisticaSentimentoResponseDto>
}