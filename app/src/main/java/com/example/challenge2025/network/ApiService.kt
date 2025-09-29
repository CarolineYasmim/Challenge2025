package com.example.challenge2025.network

import com.example.challenge2025.model.user.AuthResponse
import com.example.challenge2025.model.user.LoginRequest
import com.example.challenge2025.model.user.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

}