package com.example.challenge2025.data.remote.dto.test

import com.google.gson.annotations.SerializedName

data class TentativaRequestDto(
    @SerializedName("testeId")
    val testId: String,

    @SerializedName("respostas")
    val answers: List<RespostaBrutaDto>,

    @SerializedName("concluir")
    val isConcluded: Boolean
)

data class TentativaResponseDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("testeId")
    val testId: String,

    @SerializedName("nomeTeste")
    val testName: String,

    @SerializedName("status")
    val status: String, // ex: "INICIADA", "EM_ANDAMENTO", "CONCLUIDA"

    @SerializedName("totalRespostasSalvas")
    val savedAnswersCount: Int,

    @SerializedName("respostas")
    val answers: List<RespostaBrutaDto>,

    @SerializedName("dataInicio")
    val startDate: String, // ex: "2025-10-01T18:32:12.442Z"

    @SerializedName("dataAtualizacao")
    val updateDate: String // ex: "2025-10-01T18:32:12.442Z"
)

data class RespostaBrutaDto(
    @SerializedName("perguntaId")
    val questionId: String,

    @SerializedName("valorAtribuido")
    val value: Int
)

