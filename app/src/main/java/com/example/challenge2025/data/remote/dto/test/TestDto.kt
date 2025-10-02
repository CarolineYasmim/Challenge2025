package com.example.challenge2025.data.remote.dto.test

import com.google.gson.annotations.SerializedName

data class TesteResponseDto(
    @SerializedName("id") val id: String,
    @SerializedName("tipo") val type: String,
    @SerializedName("titulo") val title: String,
    @SerializedName("descricao") val description: String,
    @SerializedName("tempoEstimadoMinutos") val durationMinutes: Int,
    @SerializedName("perguntas") val questions: List<PerguntaResponseDto>
)

data class PerguntaResponseDto(
    @SerializedName("id") val id: String?,
    @SerializedName("texto") val text: String,
    @SerializedName("opcoes") val options: List<OpcaoRespostaResponseDto>
)

data class OpcaoRespostaResponseDto(
    @SerializedName("texto") val text: String,
    @SerializedName("valor") val value: Int
)