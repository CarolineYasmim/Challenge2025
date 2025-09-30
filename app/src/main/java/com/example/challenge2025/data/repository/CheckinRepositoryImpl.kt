package com.example.challenge2025.data.repository

import com.example.challenge2025.data.remote.ApiService
import com.example.challenge2025.data.remote.dto.checkin.CheckInDiarioRequestDto
import com.example.challenge2025.data.remote.dto.checkin.CheckInDiarioResponseDto
import com.example.challenge2025.data.remote.dto.checkin.EstatisticaSentimentoResponseDto
import com.example.challenge2025.domain.repository.CheckinRepository
import com.example.challenge2025.domain.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckinRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CheckinRepository {
    override suspend fun postCheckin(checkinData: CheckInDiarioRequestDto): Resource<CheckInDiarioResponseDto> {
        return try {
            val response = apiService.postCheckin(checkinData)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body())
            } else {
                Resource.Error("Erro ao salvar check-in: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Falha na conexão: ${e.message}")
        }
    }


    override suspend fun getCheckinsForWeek(startDate: String, endDate: String): Resource<List<CheckInDiarioResponseDto>> {
        return try {
            val response = apiService.getCheckinsForPeriod(startDate, endDate)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body())
            } else {
                Resource.Error("Erro ao buscar check-ins: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Falha na conexão: ${e.message}")
        }
    }

    override suspend fun getCheckinStatistics(days: Int): Resource<EstatisticaSentimentoResponseDto> {
        return try {
            val response = apiService.getCheckinStatistics()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body())
            } else {
                Resource.Error("Erro ao buscar estatísticas: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Falha na conexão: ${e.message}")
        }
    }
}