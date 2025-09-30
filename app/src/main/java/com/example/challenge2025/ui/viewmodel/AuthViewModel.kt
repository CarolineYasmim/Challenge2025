package com.example.challenge2025.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.data.local.AuthPreferences
import com.example.challenge2025.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val nameError: String = ""
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val prefs: AuthPreferences
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()
    fun onEmailChange(email: String) {
        _authState.value = _authState.value.copy(email = email, emailError = "")
    }

    fun onPasswordChange(password: String) {
        _authState.value = _authState.value.copy(password = password, passwordError = "")
    }

    fun onNameChange(name: String) {
        _authState.value = _authState.value.copy(name = name, nameError = "")
    }

    fun signUp(onSuccess: () -> Unit) {
        val state = _authState.value
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(state.email.trim()).matches()
        val isPasswordValid = state.password.length >= 6
        val isNameValid = state.name.isNotBlank()

        if (!isEmailValid || !isPasswordValid || !isNameValid) {
            _authState.value = _authState.value.copy(
                emailError = if (!isEmailValid) "E-mail inválido" else "",
                passwordError = if (!isPasswordValid) "Senha deve ter ao menos 6 caracteres" else "",
                nameError = if (!isNameValid) "Nome não pode estar em branco" else ""
            )
            return
        }

        viewModelScope.launch {
            try {
                repository.register(
                    name = state.name,
                    email = state.email,
                    password = state.password
                )
                onSuccess()
            } catch (e: Exception) {
                // Aqui podemos capturar melhor o erro do backend
                val errorMessage = e.message ?: "Erro desconhecido"
                _authState.value = _authState.value.copy(emailError = errorMessage)
            }
        }
    }


    fun login(onResult: (isFirstLogin: Boolean) -> Unit) {
        val state = _authState.value

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(state.email.trim()).matches()
        val isPasswordValid = state.password.isNotBlank()

        if (!isEmailValid || !isPasswordValid) {
            _authState.value = _authState.value.copy(
                emailError = if (!isEmailValid) "E-mail inválido" else "",
                passwordError = if (!isPasswordValid) "Senha não pode estar em branco" else ""
            )
            return
        }

        viewModelScope.launch {
            try {
                val response = repository.login(state.email.trim(), state.password)

                 prefs.saveAuth(response.token, state.email)

                onResult(response.isFirstLogin)
            } catch (e: Exception) {
                _authState.value = _authState.value.copy(
                    emailError = "Usuário ou senha inválidos"
                )
                println("Erro no login: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            prefs.clearAuth()
        }
    }
}

