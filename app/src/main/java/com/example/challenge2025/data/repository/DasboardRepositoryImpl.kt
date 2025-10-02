package com.example.challenge2025.data.repository

import com.example.challenge2025.data.remote.ApiService
import com.example.challenge2025.data.remote.dto.sentimentos.EstatisticaSentimentoResponseDto
import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.domain.repository.DashboardRepository
import com.example.challenge2025.domain.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DashboardRepository {

    override suspend fun getStatistics(days: Int): Resource<EstatisticaSentimentoResponseDto> {
        return try {
            val response = apiService.getCheckinStatistics(days)
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Falha na conexão")
        }
    }

    override suspend fun getTestResults(): Resource<List<ResultadoResponseDto>> {
        return try {
            val response = apiService.getAllTestResults()
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Falha na conexão")
        }
    }
}