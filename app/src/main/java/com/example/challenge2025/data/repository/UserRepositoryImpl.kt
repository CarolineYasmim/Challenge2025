// F arquivo: data/repository/UserRepositoryImpl.kt

package com.example.challenge2025.data.repository

import retrofit2.Response
import com.example.challenge2025.domain.util.Resource
import com.example.challenge2025.data.remote.dto.user.UsuarioRequestDto
import com.example.challenge2025.data.remote.ApiService
import com.example.challenge2025.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun createUserProfile(data: UsuarioRequestDto): Resource<Unit> {
        return try {
            val response: Response<Unit> = apiService.createUserProfile(data)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Erro do servidor: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Falha na conexão: ${e.message}")
        }
    }
}