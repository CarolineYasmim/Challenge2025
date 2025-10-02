// ARQUIVO MODIFICADO: data/repository/TestRepositoryImpl.kt

package com.example.challenge2025.data.repository

import com.example.challenge2025.data.mappers.toDomainModel
import com.example.challenge2025.data.mappers.toTestDetailModel
import com.example.challenge2025.data.remote.ApiService
import com.example.challenge2025.domain.model.tests.TestDetail
import com.example.challenge2025.domain.model.tests.TestItem
import com.example.challenge2025.domain.repository.TestRepository
import com.example.challenge2025.domain.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TestRepository {

    // Esta é a nova implementação que combina os dados
    override suspend fun getTestsWithUserStatus(): Resource<List<TestItem>> = coroutineScope {
        try {
            // 1. Prepara as duas chamadas de API para rodarem em paralelo
            val allTestsDeferred = async { apiService.getTests() }
            val userAttemptsDeferred = async { apiService.getAttempts() }

            // 2. Aguarda a resposta de ambas
            val allTestsResponse = allTestsDeferred.await()
            val userAttemptsResponse = userAttemptsDeferred.await()

            // 3. Verifica se ambas as chamadas foram bem-sucedidas
            if (allTestsResponse.isSuccessful && userAttemptsResponse.isSuccessful) {
                val allTestsDto = allTestsResponse.body() ?: emptyList()
                val userAttemptsDto = userAttemptsResponse.body() ?: emptyList()

                // 4. Cria o "índice de acesso rápido" com as tentativas do usuário
                val attemptsMap = userAttemptsDto.associateBy { it.testId }

                // 5. Mapeia a lista de todos os testes, cruzando com os dados do usuário
                val finalList = allTestsDto.map { testDto ->
                    val userAttempt = attemptsMap[testDto.id]
                    // Chama o "tradutor", passando a tentativa correspondente
                    testDto.toDomainModel(userAttempt = userAttempt)
                }

                Resource.Success(finalList)
            } else {
                Resource.Error("Erro ao buscar dados dos testes.")
            }
        } catch (e: Exception) {
            Resource.Error("Falha na conexão: ${e.message}")
        }
    }

    override suspend fun getTestDetails(testId: String): Resource<TestDetail> {
        return try {
            val response = apiService.getTestDetails(testId)
            if (response.isSuccessful && response.body() != null) {
                // Usa o tradutor específico para os detalhes do teste
                val testDetail = response.body()!!.toTestDetailModel()
                Resource.Success(testDetail)
            } else {
                Resource.Error("Erro ao buscar detalhes do teste: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Falha na conexão: ${e.message}")
        }
    }
}