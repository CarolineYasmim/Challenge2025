package com.example.challenge2025.data.remote.dto.checkin

import com.google.gson.annotations.SerializedName

data class CheckInDiarioResponseDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("usuarioId")
    val userId: String,

    @SerializedName("dataCheckin")
    val dateCheckin: String,

    @SerializedName("sentimentos")
    val feelings: List<Any>,

    @SerializedName("anotacao")
    val notes: String?,

    @SerializedName("dataRegistro")
    val registrationDate: String
)