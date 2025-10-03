package com.example.challenge2025.data.remote.dto.auth

data class RegisterRequest(
    val nomeCompleto: String,
    val cargo: String,
    val email: String,
    val senha: String
)