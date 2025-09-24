package com.example.challenge2025.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.challenge2025.ui.components.auth.AuthScreenLayout
import com.example.challenge2025.ui.components.auth.AuthTextField
import com.example.challenge2025.ui.components.auth.RoundedButton
import com.example.challenge2025.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val state by authViewModel.authState.collectAsState()

    AuthScreenLayout(
        title = "Entre na sua conta",
        subtitle = "Digite seus dados para fazer login"
    ) {
        AuthTextField(
            value = state.email,
            onValueChange = authViewModel::onEmailChange,
            label = "E-mail",
            isError = state.emailError != null,
            errorMessage = state.emailError,
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(
            value = state.password,
            onValueChange = authViewModel::onPasswordChange,
            label = "Senha",
            isError = state.passwordError != null,
            errorMessage = state.passwordError,
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.height(40.dp))
        RoundedButton(
            text = "Entrar",
            onClick = {
                // No futuro, authViewModel.login() fará a chamada de API
                navController.navigate("home") {
                    popUpTo("onboarding1") { inclusive = true }
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Não possui uma conta?\nClique aqui para criar uma",
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { navController.navigate("signup") }
        )
    }
}