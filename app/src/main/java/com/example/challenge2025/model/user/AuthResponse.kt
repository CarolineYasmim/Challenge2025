package com.example.challenge2025.model.user

data class AuthResponse(
    val token: String,
    val isFirstLogin: Boolean
)
