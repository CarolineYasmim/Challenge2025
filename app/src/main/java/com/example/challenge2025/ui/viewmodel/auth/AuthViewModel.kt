package com.example.challenge2025.ui.viewmodel.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.data.local.AuthPreferences
import com.example.challenge2025.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Estado simplificado APENAS com os campos de autenticação/cadastro
data class AuthState(
    val email: String = "",
    val password: String = "",
    val nomeCompleto: String = "",
    val cargo: String = "",

    val emailError: String? = null,
    val passwordError: String? = null,
    val nomeCompletoError: String? = null,
    val cargoError: String? = null,

    val isLoading: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val prefs: AuthPreferences
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    // --- Manipuladores de Eventos ---

    fun onEmailChange(email: String) { _authState.update { it.copy(email = email, emailError = null) } }
    fun onPasswordChange(password: String) { _authState.update { it.copy(password = password, passwordError = null) } }
    fun onNomeCompletoChange(nome: String) { _authState.update { it.copy(nomeCompleto = nome, nomeCompletoError = null) } }
    fun onCargoChange(cargo: String) { _authState.update { it.copy(cargo = cargo, cargoError = null) } }

    // --- Lógica de Negócio ---

    fun signUp(onSuccess: () -> Unit) {
        val state = _authState.value
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(state.email.trim()).matches()
        val isPasswordValid = state.password.length >= 6
        val isNomeCompletoValid = state.nomeCompleto.isNotBlank()
        val isCargoValid = state.cargo.isNotBlank()

        if (!isEmailValid || !isPasswordValid || !isNomeCompletoValid || !isCargoValid) {
            _authState.update {
                it.copy(
                    emailError = if (!isEmailValid) "E-mail inválido" else null,
                    passwordError = if (!isPasswordValid) "Senha deve ter ao menos 6 caracteres" else null,
                    nomeCompletoError = if (!isNomeCompletoValid) "Nome não pode estar em branco" else null,
                    cargoError = if (!isCargoValid) "Cargo não pode estar em branco" else null
                )
            }
            return
        }

        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true) }
            try {
                repository.register(
                    nomeCompleto = state.nomeCompleto.trim(),
                    cargo = state.cargo.trim(),
                    email = state.email.trim(),
                    senha = state.password
                )
                onSuccess()
            } catch (e: Exception) {
                _authState.update { it.copy(emailError = e.message ?: "Erro desconhecido") }
            } finally {
                _authState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun login(onResult: (isFirstLogin: Boolean) -> Unit) {
        val state = _authState.value

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(state.email.trim()).matches()
        val isPasswordValid = state.password.isNotBlank()

        if (!isEmailValid || !isPasswordValid) {
            _authState.update {
                it.copy(
                    emailError = if (!isEmailValid) "E-mail inválido" else null,
                    passwordError = if (!isPasswordValid) "Senha não pode estar em branco" else null
                )
            }
            return
        }

        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true) }
            try {
                val response = repository.login(state.email.trim(), state.password)
                prefs.saveAuth(response.token, state.email)
                onResult(response.isFirstLogin)
            } catch (e: Exception) {
                _authState.update {
                    it.copy(
                        emailError = "Usuário ou senha inválidos",
                        passwordError = " " // Adiciona um erro genérico para a senha também
                    )
                }
            } finally {
                _authState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            prefs.clearAuth()
        }
    }
}