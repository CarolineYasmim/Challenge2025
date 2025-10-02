package com.example.challenge2025.domain.repository

import com.example.challenge2025.data.remote.dto.test.ResultadoRequestDto
import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.domain.util.Resource

interface ResultRepository {
    suspend fun submitTestResult(resultData: ResultadoRequestDto): Resource<ResultadoResponseDto>
}