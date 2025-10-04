package com.example.challenge2025.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challenge2025.ui.components.auth.AuthScreenLayout
import com.example.challenge2025.ui.components.auth.AuthTextField
import com.example.challenge2025.ui.components.auth.RoundedButton
import com.example.challenge2025.ui.viewmodel.auth.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (isFirstLogin: Boolean) -> Unit,
    onNavigateToSignUp: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state by authViewModel.authState.collectAsState()

    AuthScreenLayout(
        title = "Entre na sua conta",
        subtitle = "Digite seus dados para fazer login"
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        AuthTextField(
            value = state.email,
            onValueChange = authViewModel::onEmailChange,
            label = "E-mail",
            // CORREÇÃO APLICADA AQUI
            isError = !state.emailError.isNullOrEmpty(),
            errorMessage = state.emailError,
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(
            value = state.password,
            onValueChange = authViewModel::onPasswordChange,
            label = "Senha",
            // CORREÇÃO APLICADA AQUI
            isError = !state.passwordError.isNullOrEmpty(),
            errorMessage = state.passwordError,
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.height(40.dp))

        RoundedButton(
            text = "Entrar",
            enabled = !state.isLoading,
            onClick = {
                authViewModel.login { isFirstLogin ->
                    onLoginSuccess(isFirstLogin)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Não possui uma conta?\nClique aqui para criar uma",
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { onNavigateToSignUp() }
        )
    }
}