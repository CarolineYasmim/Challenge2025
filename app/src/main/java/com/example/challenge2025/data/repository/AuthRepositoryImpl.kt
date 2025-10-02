

package com.example.challenge2025.data.repository

import com.example.challenge2025.data.remote.dto.auth.LoginResponseDto
import com.example.challenge2025.data.remote.dto.auth.LoginRequestDto
import com.example.challenge2025.data.remote.dto.auth.RegisterRequest
import com.example.challenge2025.data.remote.ApiService // MUDANÇA: Vamos injetar a ApiService
import com.example.challenge2025.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService // MUDANÇA 2: Recebemos a ApiService em vez de buscá-la do RetrofitInstance
) : AuthRepository { // MUDANÇA 3: Implementamos a interface

    // MUDANÇA 4: Adicionamos "override"
    override suspend fun register(name: String, email: String, password: String): LoginResponseDto {
        val request = RegisterRequest(name, email, password)
        return api.register(request)
    }

    override suspend fun login(email: String, password: String): LoginResponseDto {
        val request = LoginRequestDto(email, password)
        return api.login(request)
    }
}