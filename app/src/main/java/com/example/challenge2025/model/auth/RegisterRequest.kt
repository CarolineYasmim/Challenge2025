package com.example.challenge2025.model.auth

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)