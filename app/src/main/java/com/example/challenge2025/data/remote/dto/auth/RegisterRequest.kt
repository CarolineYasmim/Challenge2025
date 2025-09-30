package com.example.challenge2025.data.remote.dto.auth

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)