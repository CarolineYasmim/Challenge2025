package com.example.challenge2025.repository

import com.example.challenge2025.model.auth.AuthResponse

// MUDANÇA: Criamos a interface para abstrair a implementação
interface AuthRepository {
    suspend fun register(name: String, email: String, password: String): AuthResponse
    suspend fun login(email: String, password: String): AuthResponse
}