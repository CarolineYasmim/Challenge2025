// F arquivo: data/repository/UserRepositoryImpl.kt

package com.example.challenge2025.repository

import com.bumptech.glide.load.engine.Resource
import com.example.challenge2025.model.user.CreateUserRequest
import com.example.challenge2025.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // Garante que só teremos uma instância
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun createUserProfile(data: CreateUserRequest): Resource<Unit> {
        // MUDANÇA 2: Lógica de tratamento de resposta corrigida
        return try {
            val response = apiService.createUserProfile(data) // Retrofit retorna um Response
            if (response.isSuccessful) {
                Resource.Success(Unit) // Se a resposta for sucesso, retornamos nosso Resource.Success
            } else {
                Resource.Error("Erro do servidor: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Falha na conexão: ${e.message}")
        }
    }
}