package com.example.challenge2025.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.challenge2025.ui.components.auth.AuthTextField
import com.example.challenge2025.ui.components.auth.RoundedButton
import com.example.challenge2025.ui.components.onboarding.StepLayout
import com.example.challenge2025.domain.util.Resource
import com.example.challenge2025.ui.viewmodel.UserViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    totalSteps: Int
) {
    val state by userViewModel.onboardingState.collectAsState()
    val createState by userViewModel.createUserState.collectAsState()
    val currentStep = 6

    LaunchedEffect(createState) {
        val result = createState

        when (result) {
            is Resource.Success -> {
                userViewModel.nextStep()
            }
            is Resource.Error -> {
                println("Erro ao finalizar cadastro: ${result.message}")
            }
            else -> { /* Não faz nada */ }
        }
    }

    var companyExpanded by remember { mutableStateOf(false) }
    var departmentExpanded by remember { mutableStateOf(false) }
    var roleExpanded by remember { mutableStateOf(false) }

    val areDetailsEnabled = state.company.isNotBlank() &&
            state.company != "Não estou trabalhando" &&
            state.company != "Trabalho por conta própria"

    // Data formatter e estado do input com máscara
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var entryDateInput by remember {
        mutableStateOf(state.entryDate?.format(dateFormatter) ?: "")
    }
    var entryDateError by remember { mutableStateOf<String?>(null) }

    StepLayout(
        navController = navController,
        viewModel = userViewModel,
        currentStep = currentStep,
        totalSteps = totalSteps,
        title = "Sua Posição na Empresa",
        subtitle = "Essas informações são usadas exclusivamente para gerar relatórios anônimos."
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- Empresa (editável + dropdown) ---
                ExposedDropdownMenuBox(
                    expanded = companyExpanded,
                    onExpandedChange = { companyExpanded = !it }
                ) {
                    AuthTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
                        value = state.company,
                        onValueChange = {
                            userViewModel.onCompanyChange(it)
                            companyExpanded = true
                        },
                        label = "Empresa",
                        readOnly = false,
                        enabled = true
                    )

                    val filteredCompanies = userViewModel.companies.filter {
                        it.contains(state.company, ignoreCase = true)
                    }
                    if (filteredCompanies.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = companyExpanded,
                            onDismissRequest = { companyExpanded = false }
                        ) {
                            filteredCompanies.forEach { company ->
                                DropdownMenuItem(
                                    text = { Text(company) },
                                    onClick = {
                                        userViewModel.onCompanyChange(company)
                                        companyExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }


                // --- Setor (editável + dropdown) ---
                ExposedDropdownMenuBox(
                    expanded = departmentExpanded,
                    onExpandedChange = { if (areDetailsEnabled) departmentExpanded = !it }
                ) {
                    val filteredDepartments = userViewModel.departments.filter {
                        it.contains(state.department, ignoreCase = true)
                    }
                    AuthTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = areDetailsEnabled),
                        value = state.department,
                        onValueChange = {
                            userViewModel.onDepartmentChange(it)
                            departmentExpanded = true
                        },
                        label = "Setor/Departamento",
                        readOnly = false,
                        enabled = areDetailsEnabled
                    )
                    if (filteredDepartments.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = departmentExpanded,
                            onDismissRequest = { departmentExpanded = false }
                        ) {
                            filteredDepartments.forEach { department ->
                                DropdownMenuItem(
                                    text = { Text(department) },
                                    onClick = {
                                        userViewModel.onDepartmentChange(department)
                                        departmentExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // --- Cargo (editável + dropdown) ---
                ExposedDropdownMenuBox(
                    expanded = roleExpanded,
                    onExpandedChange = { if (areDetailsEnabled) roleExpanded = !it }
                ) {
                    val filteredRoles = userViewModel.roles.filter {
                        it.contains(state.role, ignoreCase = true)
                    }
                    AuthTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = areDetailsEnabled),
                        value = state.role,
                        onValueChange = {
                            userViewModel.onRoleChange(it)
                            roleExpanded = true
                        },
                        label = "Cargo/Função",
                        readOnly = false,
                        enabled = areDetailsEnabled
                    )
                    if (filteredRoles.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = roleExpanded,
                            onDismissRequest = { roleExpanded = false }
                        ) {
                            filteredRoles.forEach { role ->
                                DropdownMenuItem(
                                    text = { Text(role) },
                                    onClick = {
                                        userViewModel.onRoleChange(role)
                                        roleExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // --- Data de Entrada (campo texto com máscara dd/MM/yyyy) ---
                AuthTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = entryDateInput,
                    onValueChange = { input ->
                        // mantém só dígitos e monta com slashes automaticamente
                        val digits = input.filter { it.isDigit() }.take(8)
                        val sb = StringBuilder()
                        for (i in digits.indices) {
                            sb.append(digits[i])
                            if (i == 1 || i == 3) sb.append('/')
                        }
                        val formatted = sb.toString()
                        entryDateInput = if (formatted.length <= 10) formatted else formatted.substring(0, 10)

                        // validação apenas quando já tem 10 chars (dd/MM/yyyy)
                        if (entryDateInput.length == 10) {
                            try {
                                val parsed = LocalDate.parse(entryDateInput, dateFormatter)
                                // só atualiza o viewModel quando a data for válida
                                userViewModel.onEntryDateChange(parsed)
                                entryDateError = null
                            } catch (_: DateTimeParseException) {
                                entryDateError = "Data inválida"
                            }
                        } else {
                            entryDateError = null
                        }
                    },
                    label = "Data de entrada (dd/MM/yyyy)",
                    readOnly = false,
                    enabled = areDetailsEnabled,
                    isError = entryDateError != null,
                    errorMessage = entryDateError
                )
            }

            RoundedButton(
                text = "Finalizar",
                onClick = { userViewModel.finalizeOnboarding() },
                enabled = createState !is Resource.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
            )
        }
    }
}
