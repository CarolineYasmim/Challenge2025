package com.example.challenge2025.model.user

data class User(
    val id: String,
    val name: String,
    val avatarRes: Int,
    val bannerRes: Int,
    val status: String,
    val description: String,
    val tags: List<String>
)