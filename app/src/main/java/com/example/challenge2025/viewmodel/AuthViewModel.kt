package com.example.challenge2025.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Estado que representa tudo na UI de login/cadastro
data class AuthState(
    val currentStep: Int = 1,
    // Etapa 1
    val name: String = "",
    val email: String = "",
    val password: String = "",
    // Etapa 2
    val company: String = "",
    val department: String = "",
    val role: String = "",
    // Erros
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    // Mock de dados para os Dropdowns
    val companies = listOf("Empresa A", "Empresa B", "Empresa C")
    val departments = listOf("RH", "TI", "Marketing", "Vendas")
    val roles = listOf("Analista", "Gerente", "Estagiário", "Coordenador")


    fun onNameChange(name: String) {
        _authState.update { it.copy(name = name, nameError = null) }
    }

    fun onEmailChange(email: String) {
        _authState.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        _authState.update { it.copy(password = password, passwordError = null) }
    }

    fun onCompanyChange(company: String) {
        _authState.update { it.copy(company = company) }
    }

    fun onDepartmentChange(department: String) {
        _authState.update { it.copy(department = department) }
    }

    fun onRoleChange(role: String) {
        _authState.update { it.copy(role = role) }
    }

    fun nextStep() {
        if (validateCurrentStep()) {
            _authState.update { it.copy(currentStep = it.currentStep + 1) }
        }
    }

    fun previousStep() {
        _authState.update { it.copy(currentStep = it.currentStep - 1) }
    }

    private fun validateCurrentStep(): Boolean {
        return when (_authState.value.currentStep) {
            1 -> {
                if (_authState.value.name.isBlank()) {
                    _authState.update { it.copy(nameError = "Nome não pode estar em branco") }
                    return false
                }
                if (_authState.value.email.isBlank() || !_authState.value.email.contains("@")) {
                    _authState.update { it.copy(emailError = "E-mail inválido") }
                    return false
                }
                if (_authState.value.password.length < 6) {
                    _authState.update { it.copy(passwordError = "Senha deve ter no mínimo 6 caracteres") }
                    return false
                }
                true
            }
            // Adicionar validações para outros passos se necessário
            else -> true
        }
    }

    // Funções de login/cadastro que chamarão o backend no futuro
    fun login() { /* TODO: Lógica de Login */ }
    fun signUp() { /* TODO: Lógica de Cadastro */ }
}