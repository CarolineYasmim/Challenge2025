package com.example.challenge2025.model.user

data class User(
    val id: String,
    val name: String,

    // --- MUDANÇA APLICADA AQUI ---
    val avatarUrl: String?, // Para a imagem vinda do backend (pode ser nula)
    val avatarRes: Int,     // Para a imagem padrão local (nunca nula)
    // ---------------------------

    val bannerRes: Int,
    val status: String,
    val description: String,
    val tags: List<String>
)