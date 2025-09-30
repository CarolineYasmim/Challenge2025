package com.example.challenge2025.data.remote.dto.checkin

data class DetalheSentimentoDto(
    @SerializedName("sentimento")
    val feeling: String,

    @SerializedName("contagem")
    val count: Int,

    @SerializedName("porcentagem")
    val percentage: Double // Usamos Double para porcentagens
)