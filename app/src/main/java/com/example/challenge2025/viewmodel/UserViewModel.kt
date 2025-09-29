package com.example.challenge2025.viewmodel

import androidx.lifecycle.ViewModel
import com.example.challenge2025.R
import com.example.challenge2025.model.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

data class OnboardingState(
    val currentStep: Int = 1,
    val selectedMotivations: Set<String> = emptySet(),
    val age: String = "",
    val gender: String = "",
    val company: String = "",
    val department: String = "",
    val role: String = "",
    val entryDate: LocalDate? = null,
    val termsAccepted: Boolean = false,
    val profilePictureUri: String? = null,
    val ageError: String? = null
)


class UserViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow(
        User(
            id = "1",
            name = "João Silva",
            status = "Ativo",
            description = "Bem-vindo ao app!",
            avatarUrl = null,
            avatarRes = R.drawable.default_avatar,
            bannerRes = R.drawable.default_banner,
            tags = listOf("Bem-estar", "Produtividade")
        )
    )
    val currentUser = _currentUser.asStateFlow()

    private val _onboardingState = MutableStateFlow(OnboardingState())
    val onboardingState = _onboardingState.asStateFlow()

    val motivationOptions = listOf(
        "Entender meus sentimentos",
        "Reduzir o estresse e a ansiedade",
        "Melhorar meu foco e produtividade",
        "Desenvolver mais autoconfiança",
        "Ajudar o meu ambiente de trabalho",
        "Outro"
    )
    val genderOptions = listOf("Feminino", "Masculino", "Outro", "Prefiro não dizer")
    val companies = listOf("Empresa A", "Empresa B", "Empresa C", "Não estou trabalhando", "Trabalho por conta própria")
    val departments = listOf("RH", "TI", "Marketing", "Vendas", "Não se aplica")
    val roles = listOf("Analista", "Gerente", "Estagiário", "Coordenador", "Não se aplica")

    // --- Funções para onboarding ---
    fun toggleMotivationSelection(option: String) {
        _onboardingState.value = _onboardingState.value.copy(
            selectedMotivations = _onboardingState.value.selectedMotivations.toMutableSet().apply {
                if (contains(option)) remove(option) else add(option)
            }
        )
    }

    fun onAgeChange(ageText: String) {
        if (ageText.length <= 3 && ageText.all { it.isDigit() }) {
            _onboardingState.value = _onboardingState.value.copy(age = ageText, ageError = null)
        }
    }

    fun onGenderChange(gender: String) {
        _onboardingState.value = _onboardingState.value.copy(gender = gender)
    }

    fun onCompanyChange(company: String) {
        if (company == "Não estou trabalhando" || company == "Trabalho por conta própria") {
            _onboardingState.value = _onboardingState.value.copy(
                company = company,
                department = "",
                role = "",
                entryDate = null
            )
        } else {
            _onboardingState.value = _onboardingState.value.copy(company = company)
        }
    }

    fun onDepartmentChange(department: String) {
        _onboardingState.value = _onboardingState.value.copy(department = department)
    }

    fun onRoleChange(role: String) {
        _onboardingState.value = _onboardingState.value.copy(role = role)
    }

    fun onEntryDateChange(date: LocalDate) {
        _onboardingState.value = _onboardingState.value.copy(entryDate = date)
    }

    fun onTermsAcceptedChange(isAccepted: Boolean) {
        _onboardingState.value = _onboardingState.value.copy(termsAccepted = isAccepted)
    }

    fun updateProfilePicture(uri: String) {
        _onboardingState.value = _onboardingState.value.copy(profilePictureUri = uri)
        _currentUser.value = _currentUser.value.copy(avatarUrl = uri)
    }

    // --- Controle de passos ---
    fun nextStep() {
        if (validateCurrentStep()) {
            _onboardingState.value = _onboardingState.value.copy(currentStep = _onboardingState.value.currentStep + 1)
        }
    }

    fun previousStep() {
        _onboardingState.value = _onboardingState.value.copy(currentStep = _onboardingState.value.currentStep - 1)
    }

    private fun validateCurrentStep(): Boolean {
        val state = _onboardingState.value
        return when (state.currentStep) {
            1 -> state.selectedMotivations.isNotEmpty()
            2 -> {
                val ageValid = state.age.toIntOrNull() in 0..110
                val genderValid = state.gender.isNotBlank()
                if (!ageValid) _onboardingState.value = state.copy(ageError = "Idade inválida")
                ageValid && genderValid
            }
            3 -> true // tela de engajamento
            4 -> true // credenciais (login/signup) já tratadas no AuthViewModel
            5 -> state.termsAccepted
            6 -> {
                if (state.company == "Não estou trabalhando" || state.company == "Trabalho por conta própria") {
                    state.company.isNotBlank()
                } else {
                    state.company.isNotBlank() && state.department.isNotBlank() && state.role.isNotBlank() && state.entryDate != null
                }
            }
            else -> true
        }
    }
}
