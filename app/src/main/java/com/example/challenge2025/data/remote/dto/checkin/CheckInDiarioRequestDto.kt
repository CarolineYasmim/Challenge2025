package com.example.challenge2025.data.remote.dto.checkin

import com.google.gson.annotations.SerializedName

data class CheckInDiarioRequestDto(
    @SerializedName("sentimentos")
    val feelingIds: List<String>, // Lista de IDs como "f1", "f5"

    @SerializedName("anotacao")
    val notes: String?
)