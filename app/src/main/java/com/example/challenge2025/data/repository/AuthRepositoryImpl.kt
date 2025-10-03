package com.example.challenge2025.data.repository

import com.example.challenge2025.data.remote.ApiService
import com.example.challenge2025.data.remote.dto.auth.LoginRequestDto
import com.example.challenge2025.data.remote.dto.auth.LoginResponseDto
import com.example.challenge2025.data.remote.dto.auth.RegisterRequest
import com.example.challenge2025.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService
) : AuthRepository {

    // MUDANÇA: Assinatura do método e corpo atualizados
    override suspend fun register(nomeCompleto: String, cargo: String, email: String, senha: String): LoginResponseDto {
        val request = RegisterRequest(
            nomeCompleto = nomeCompleto,
            cargo = cargo,
            email = email,
            senha = senha
        )
        return api.register(request)
    }

    override suspend fun login(email: String, password: String): LoginResponseDto {
        val request = LoginRequestDto(email, password)
        return api.login(request)
    }
}