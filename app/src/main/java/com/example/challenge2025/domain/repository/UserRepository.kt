package com.example.challenge2025.domain.repository

import com.example.challenge2025.data.remote.dto.user.UsuarioRequestDto
import com.example.challenge2025.domain.util.Resource

// A interface define o contrato
interface UserRepository {
    suspend fun createUserProfile(data: UsuarioRequestDto): Resource<Unit>
}