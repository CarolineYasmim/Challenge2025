package com.example.challenge2025.data.remote

import com.example.challenge2025.data.remote.dto.test.TesteResponseDto
import com.example.challenge2025.data.remote.dto.auth.LoginRequestDto
import com.example.challenge2025.data.remote.dto.auth.LoginResponseDto
import com.example.challenge2025.data.remote.dto.auth.RegisterRequest
import com.example.challenge2025.data.remote.dto.checkin.CheckInDiarioRequestDto
import com.example.challenge2025.data.remote.dto.checkin.CheckInDiarioResponseDto
import com.example.challenge2025.data.remote.dto.checkin.EstatisticaSentimentoResponseDto
import com.example.challenge2025.data.remote.dto.test.ResultadoRequestDto
import com.example.challenge2025.data.remote.dto.test.ResultadoResponseDto
import com.example.challenge2025.data.remote.dto.test.TentativaRequestDto
import com.example.challenge2025.data.remote.dto.test.TentativaResponseDto
import com.example.challenge2025.data.remote.dto.user.UsuarioRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): LoginResponseDto

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @POST("/api/usuarios")
    suspend fun createUserProfile(@Body request: UsuarioRequestDto): Response<Unit>

    @POST("/api/checkins")
    suspend fun postCheckin(@Body checkinRequest: CheckInDiarioRequestDto): Response<CheckInDiarioResponseDto>

    @GET("/api/checkins/estatisticas")
    suspend fun getCheckinStatistics(): Response<EstatisticaSentimentoResponseDto>

    @GET("/api/checkins")
    suspend fun getCheckinsForPeriod(
        @Query("dataInicial") startDate: String,
        @Query("dataFinal") endDate: String
    ): Response<List<CheckInDiarioResponseDto>>

    @GET("/api/testes")
    suspend fun getTests(@Query("filtro") filter: String? = null): Response<List<TesteResponseDto>>

    @GET("/api/testes/{id}")
    suspend fun getTestDetails(@Path("id") testId: String): Response<TesteResponseDto>

    @GET("/api/tentativas")
    suspend fun getAttempts(): Response<List<TentativaResponseDto>>

    @POST("/api/tentativas")
    suspend fun startOrUpdateAttempt(@Body request: TentativaRequestDto): Response<TentativaResponseDto>

    @POST("/api/resultados")
    suspend fun submitTestResult(@Body resultRequest: ResultadoRequestDto): Response<ResultadoResponseDto>


}