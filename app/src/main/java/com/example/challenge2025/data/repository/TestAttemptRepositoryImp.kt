package com.example.challenge2025.data.repository

import com.example.challenge2025.data.remote.ApiService
import com.example.challenge2025.data.remote.dto.test.TentativaRequestDto
import com.example.challenge2025.data.remote.dto.test.TentativaResponseDto
import com.example.challenge2025.domain.repository.TestAttemptRepository
import com.example.challenge2025.domain.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestAttemptRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TestAttemptRepository {

    override suspend fun startAttempt(testId: String): Resource<TentativaResponseDto> {
        return try {
            val request = TentativaRequestDto(
                testId = testId,
                answers = emptyList(),
                isConcluded = false
            )
            val response = apiService.startOrUpdateAttempt(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body())
            } else {
                Resource.Error("Erro ao iniciar tentativa: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Falha na conexão: ${e.message}")
        }
    }
}