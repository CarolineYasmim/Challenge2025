package com.example.challenge2025.model.auth

data class AuthResponse(
    val token: String,
    val isFirstLogin: Boolean
)