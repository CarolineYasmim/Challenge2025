package com.example.challenge2025.data.remote.dto.user

import com.google.gson.annotations.SerializedName

data class UsuarioRequestDto(
    @SerializedName("idade")
    val age: Int,
    @SerializedName("genero")
    val gender: String,
    @SerializedName("empresa")
    val company: String,
    @SerializedName("setor")
    val department: String,
    @SerializedName("cargo")
    val role: String,
    @SerializedName("data_de_entrada")
    val entryDate: String?
)