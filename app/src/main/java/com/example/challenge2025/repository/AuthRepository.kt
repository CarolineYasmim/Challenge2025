package com.example.challenge2025.repository

import com.example.challenge2025.model.user.AuthResponse
import com.example.challenge2025.model.user.LoginRequest
import com.example.challenge2025.model.user.RegisterRequest
import com.example.challenge2025.network.RetrofitInstance

class AuthRepository {
    private val api = RetrofitInstance.api

    suspend fun register(name: String, email: String, password: String): AuthResponse {
        val request = RegisterRequest(name, email, password)
        return api.register(request)
    }

    suspend fun login(email: String, password: String): AuthResponse {
        val request = LoginRequest(email, password)
        return api.login(request)
    }
}
