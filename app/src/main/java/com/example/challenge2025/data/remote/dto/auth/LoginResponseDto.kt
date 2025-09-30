package com.example.challenge2025.data.remote.dto.auth

data class LoginResponseDto(
    val token: String,
    val isFirstLogin: Boolean
)