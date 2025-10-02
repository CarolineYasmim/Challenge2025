package com.example.challenge2025.domain.repository

import com.example.challenge2025.data.remote.dto.test.TentativaResponseDto
import com.example.challenge2025.domain.util.Resource

interface TestAttemptRepository {
    suspend fun startAttempt(testId: String): Resource<TentativaResponseDto>
}