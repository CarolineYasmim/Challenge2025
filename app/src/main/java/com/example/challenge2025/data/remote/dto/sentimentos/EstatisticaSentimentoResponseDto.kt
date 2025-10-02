package com.example.challenge2025.data.remote.dto.sentimentos

import com.example.challenge2025.data.remote.dto.sentimentos.DetalheSentimentoDto
import com.google.gson.annotations.SerializedName

data class EstatisticaSentimentoResponseDto(
    @SerializedName("sentimentoFrequente")
    val frequentFeeling: String,

    @SerializedName("detalhes")
    val details: List<DetalheSentimentoDto>
)