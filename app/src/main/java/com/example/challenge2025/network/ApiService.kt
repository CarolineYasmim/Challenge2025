package com.example.challenge2025.network

import com.android.volley.Response
import com.example.challenge2025.model.auth.AuthResponse
import com.example.challenge2025.model.auth.LoginRequest
import com.example.challenge2025.model.auth.RegisterRequest
import com.example.challenge2025.model.user.CreateUserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("api/user/create")
    suspend fun createUserProfile(@Body request: CreateUserRequest): Response<Unit> //colocar o endpont correto
}