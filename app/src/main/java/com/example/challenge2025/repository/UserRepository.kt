package com.example.challenge2025.repository

import com.example.challenge2025.model.user.CreateUserRequest
import com.example.challenge2025.util.Resource

// A interface define o contrato
interface UserRepository {
    suspend fun createUserProfile(data: CreateUserRequest): Resource<Unit>
}