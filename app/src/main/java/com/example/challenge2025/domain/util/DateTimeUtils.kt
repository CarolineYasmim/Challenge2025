package com.example.challenge2025.domain.util

// Converte tempo em milissegundos para mm:ss
fun formatTimeMillisToMinutesSeconds(timeMillis: Long): String {
    val seconds = (timeMillis / 1000) % 60
    val minutes = (timeMillis / 1000) / 60
    return "%02d:%02d".format(minutes, seconds)
}
