package com.example.challenge2025.domain.repository

import com.example.challenge2025.data.remote.dto.auth.LoginResponseDto

// MUDANÇA: Criamos a interface para abstrair a implementação
interface AuthRepository {
    suspend fun register(name: String, email: String, password: String): LoginResponseDto
    suspend fun login(email: String, password: String): LoginResponseDto
}