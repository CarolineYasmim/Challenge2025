package com.example.challenge2025.model.user

import com.google.gson.annotations.SerializedName

// Classe para ENVIAR os dados para o backend (permanece igual)
data class CreateUserRequest(
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