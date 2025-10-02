package com.example.challenge2025.data.repository

import com.example.challenge2025.data.remote.ApiService
import com.example.challenge2025.data.remote.dto.test.ResultadoRequestDto
import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.domain.repository.ResultRepository
import com.example.challenge2025.domain.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ResultRepository {

    override suspend fun submitTestResult(resultData: ResultadoRequestDto): Resource<ResultadoResponseDto> {
        return try {
            val response = apiService.submitTestResult(resultData)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body())
            } else {
                Resource.Error("Erro ao submeter resultado: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Falha na conexão: ${e.message}")
        }
    }
}