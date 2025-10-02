package com.example.challenge2025.domain.util

// Esta classe vai nos ajudar a saber o estado da nossa chamada de rede
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}