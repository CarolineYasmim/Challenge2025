
package com.example.challenge2025.data.remote.dto.test

import com.google.gson.annotations.SerializedName

data class ResultadoRequestDto(
    @SerializedName("testeId")
    val testId: String,

    @SerializedName("respostas")
    val answers: List<RespostaUsuarioResponseDto>
)

data class RespostaUsuarioResponseDto(
    @SerializedName("perguntaId")
    val questionId: String,

    @SerializedName("perguntaTexto")
    val questionText: String,

    @SerializedName("respostaSelecionada")
    val selectedAnswerText: String,

    @SerializedName("valorAtribuido")
    val value: Int
)

data class ResultadoResponseDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("tipoTeste")
    val testType: String,

    @SerializedName("testeId")
    val testId: String,

    @SerializedName("usuarioId")
    val userId: String,

    @SerializedName("dataRealizacao")
    val completionDate: String,

    @SerializedName("pontuacaoTotal")
    val totalScore: Int,

    @SerializedName("nivelRisco")
    val riskLevel: String,

    @SerializedName("respostas")
    val answers: List<RespostaUsuarioResponseDto>
)