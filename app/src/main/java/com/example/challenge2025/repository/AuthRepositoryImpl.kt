// ARQUIVO MODIFICADO: repository/AuthRepositoryImpl.kt

package com.example.challenge2025.repository

import com.example.challenge2025.model.auth.AuthResponse
import com.example.challenge2025.model.auth.LoginRequest
import com.example.challenge2025.model.auth.RegisterRequest
import com.example.challenge2025.network.ApiService // MUDANÇA: Vamos injetar a ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // MUDANÇA 1: Garante que só haverá uma instância deste repositório no app
class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService // MUDANÇA 2: Recebemos a ApiService em vez de buscá-la do RetrofitInstance
) : AuthRepository { // MUDANÇA 3: Implementamos a interface

    // MUDANÇA 4: Adicionamos "override"
    override suspend fun register(name: String, email: String, password: String): AuthResponse {
        val request = RegisterRequest(name, email, password)
        return api.register(request)
    }

    override suspend fun login(email: String, password: String): AuthResponse {
        val request = LoginRequest(email, password)
        return api.login(request)
    }
}