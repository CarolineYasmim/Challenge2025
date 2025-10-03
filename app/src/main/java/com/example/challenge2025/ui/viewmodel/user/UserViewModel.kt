package com.example.challenge2025.ui.viewmodel.user

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
    val nomeCompleto: String = "", // MUDANÇA: 'name' -> 'nomeCompleto'
    val cargo: String = "",        // MUDANÇA: Novo campo
    val emailError: String = "",
    val passwordError: String = "",
    val nomeCompletoError: String = "", // MUDANÇA: 'nameError' -> 'nomeCompletoError'
    val cargoError: String = ""         // MUDANÇA: Novo campo
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

    // MUDANÇA: Renomeada de 'onNameChange'
    fun onNomeCompletoChange(nome: String) {
        _authState.value = _authState.value.copy(nomeCompleto = nome, nomeCompletoError = "")
    }

    // MUDANÇA: Nova função para o campo 'cargo'
    fun onCargoChange(cargo: String) {
        _authState.value = _authState.value.copy(cargo = cargo, cargoError = "")
    }

    fun signUp(onSuccess: () -> Unit) {
        val state = _authState.value
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(state.email.trim()).matches()
        val isPasswordValid = state.password.length >= 6
        val isNomeCompletoValid = state.nomeCompleto.isNotBlank()
        val isCargoValid = state.cargo.isNotBlank() // MUDANÇA: Validação do cargo

        if (!isEmailValid || !isPasswordValid || !isNomeCompletoValid || !isCargoValid) {
            _authState.value = _authState.value.copy(
                emailError = if (!isEmailValid) "E-mail inválido" else "",
                passwordError = if (!isPasswordValid) "Senha deve ter ao menos 6 caracteres" else "",
                nomeCompletoError = if (!isNomeCompletoValid) "Nome não pode estar em branco" else "",
                cargoError = if (!isCargoValid) "Cargo não pode estar em branco" else ""
            )
            return
        }

        viewModelScope.launch {
            try {
                // MUDANÇA: Passando todos os parâmetros necessários
                repository.register(
                    nomeCompleto = state.nomeCompleto,
                    cargo = state.cargo,
                    email = state.email,
                    senha = state.password
                )
                onSuccess()
            } catch (e: Exception) {
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