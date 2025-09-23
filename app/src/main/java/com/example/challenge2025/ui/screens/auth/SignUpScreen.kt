package com.example.challenge2025.ui.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.challenge2025.ui.components.AuthScreenLayout
import com.example.challenge2025.ui.components.AuthTextField
import com.example.challenge2025.ui.components.RoundedButton
import com.example.challenge2025.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val state by authViewModel.authState.collectAsState()

    AnimatedContent(targetState = state.currentStep, label = "signup_step_animation") { step ->
        when (step) {
            1 -> SignUpStep1(authViewModel, navController)
            2 -> SignUpStep2(authViewModel)
            3 -> SignUpStep3(authViewModel, navController)
        }
    }
}

@Composable
private fun SignUpStep1(viewModel: AuthViewModel, navController: NavController) {
    val state by viewModel.authState.collectAsState()
    AuthScreenLayout(title = "Crie sua Conta (1/3)", subtitle = "Vamos começar com o básico") {
        AuthTextField(modifier = Modifier.fillMaxWidth(), value = state.name, onValueChange = viewModel::onNameChange, label = "Nome Completo", isError = state.nameError != null, errorMessage = state.nameError)
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(modifier = Modifier.fillMaxWidth(), value = state.email, onValueChange = viewModel::onEmailChange, label = "E-mail", isError = state.emailError != null, errorMessage = state.emailError, keyboardType = KeyboardType.Email)
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(modifier = Modifier.fillMaxWidth(), value = state.password, onValueChange = viewModel::onPasswordChange, label = "Senha", isError = state.passwordError != null, errorMessage = state.passwordError, keyboardType = KeyboardType.Password)
        Spacer(modifier = Modifier.height(40.dp))
        RoundedButton(text = "Próximo", onClick = viewModel::nextStep)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Já possui uma conta? Faça login", color = MaterialTheme.colorScheme.primary, modifier = Modifier.clickable { navController.navigate("login") })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpStep2(viewModel: AuthViewModel) {
    val state by viewModel.authState.collectAsState()
    var companyExpanded by remember { mutableStateOf(false) }
    var departmentExpanded by remember { mutableStateOf(false) }
    var roleExpanded by remember { mutableStateOf(false) }

    AuthScreenLayout(title = "Contexto (2/3)", subtitle = "Estas informações nos ajudam a gerar análises anônimas para melhorar seu ambiente de trabalho.") {
        // Dropdown para Empresa
        ExposedDropdownMenuBox(expanded = companyExpanded, onExpandedChange = { companyExpanded = !companyExpanded }) {
            AuthTextField(
                // CORREÇÃO APLICADA AQUI
                modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                value = state.company,
                onValueChange = {},
                label = "Empresa",
                readOnly = true
            )
            ExposedDropdownMenu(expanded = companyExpanded, onDismissRequest = { companyExpanded = false }) {
                viewModel.companies.forEach { company ->
                    DropdownMenuItem(text = { Text(company) }, onClick = {
                        viewModel.onCompanyChange(company)
                        companyExpanded = false
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Dropdown para Setor
        ExposedDropdownMenuBox(expanded = departmentExpanded, onExpandedChange = { departmentExpanded = !departmentExpanded }) {
            AuthTextField(
                // CORREÇÃO APLICADA AQUI
                modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                value = state.department,
                onValueChange = {},
                label = "Setor/Departamento",
                readOnly = true
            )
            ExposedDropdownMenu(expanded = departmentExpanded, onDismissRequest = { departmentExpanded = false }) {
                viewModel.departments.forEach { department ->
                    DropdownMenuItem(text = { Text(department) }, onClick = {
                        viewModel.onDepartmentChange(department)
                        departmentExpanded = false
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Dropdown para Cargo
        ExposedDropdownMenuBox(expanded = roleExpanded, onExpandedChange = { roleExpanded = !roleExpanded }) {
            AuthTextField(
                // CORREÇÃO APLICADA AQUI
                modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                value = state.role,
                onValueChange = {},
                label = "Cargo/Função",
                readOnly = true
            )
            ExposedDropdownMenu(expanded = roleExpanded, onDismissRequest = { roleExpanded = false }) {
                viewModel.roles.forEach { role ->
                    DropdownMenuItem(text = { Text(role) }, onClick = {
                        viewModel.onRoleChange(role)
                        roleExpanded = false
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = viewModel::previousStep, modifier = Modifier.weight(1f)) { Text("Voltar") }
            Spacer(modifier = Modifier.width(16.dp))
            RoundedButton(text = "Próximo", onClick = viewModel::nextStep, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun SignUpStep3(viewModel: AuthViewModel, navController: NavController) {
    AuthScreenLayout(title = "Finalizando (3/3)", subtitle = "Estes dados opcionais e anônimos nos ajudam a entender melhor os desafios de bem-estar.") {
        Text("Aqui você poderá adicionar informações demográficas opcionais.", textAlign = TextAlign.Center, modifier = Modifier.padding(vertical = 40.dp))
        Spacer(modifier = Modifier.height(40.dp))
        Row(Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = viewModel::previousStep, modifier = Modifier.weight(1f)) { Text("Voltar") }
            Spacer(modifier = Modifier.width(16.dp))
            RoundedButton(text = "Finalizar Cadastro", onClick = {
                navController.navigate("home") {
                    popUpTo("onboarding1") { inclusive = true }
                }
            }, modifier = Modifier.weight(1f))
        }
    }
}