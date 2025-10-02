package com.example.challenge2025.domain.repository

import com.example.challenge2025.data.remote.dto.sentimentos.EstatisticaSentimentoResponseDto
import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.domain.util.Resource

interface DashboardRepository {
    suspend fun getStatistics(days: Int): Resource<EstatisticaSentimentoResponseDto>
    suspend fun getTestResults(): Resource<List<ResultadoResponseDto>>
}