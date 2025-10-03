package com.example.challenge2025.domain.repository

import com.example.challenge2025.data.remote.dto.auth.LoginResponseDto

interface AuthRepository {
    // MUDANÇA: Adicionados novos parâmetros
    suspend fun register(nomeCompleto: String, cargo: String, email: String, senha: String): LoginResponseDto
    suspend fun login(email: String, password: String): LoginResponseDto
}