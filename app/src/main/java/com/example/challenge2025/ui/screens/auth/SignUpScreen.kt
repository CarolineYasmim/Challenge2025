package com.example.challenge2025.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challenge2025.ui.components.auth.AuthScreenLayout
import com.example.challenge2025.ui.components.auth.AuthTextField
import com.example.challenge2025.ui.components.auth.RoundedButton
import com.example.challenge2025.ui.viewmodel.auth.AuthViewModel

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state by authViewModel.authState.collectAsState()

    AuthScreenLayout(
        title = "Faça seu cadastro",
        subtitle = "Preencha seus dados para criar uma conta"
    ) {
        // Adicionado para caso o teclado empurre os campos para cima
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AuthTextField(
                    value = state.nomeCompleto,
                    onValueChange = authViewModel::onNomeCompletoChange,
                    label = "Nome completo",
                    isError = state.nomeCompletoError != null,
                    errorMessage = state.nomeCompletoError
                )

                AuthTextField(
                    value = state.cargo,
                    onValueChange = authViewModel::onCargoChange,
                    label = "Seu cargo",
                    isError = state.cargoError != null,
                    errorMessage = state.cargoError
                )

                AuthTextField(
                    value = state.email,
                    onValueChange = authViewModel::onEmailChange,
                    label = "Seu e-mail",
                    isError = state.emailError != null,
                    errorMessage = state.emailError,
                    keyboardType = KeyboardType.Email
                )

                AuthTextField(
                    value = state.password,
                    onValueChange = authViewModel::onPasswordChange,
                    label = "Crie uma senha",
                    isError = state.passwordError != null,
                    errorMessage = state.passwordError,
                    keyboardType = KeyboardType.Password
                )
            }

            Spacer(Modifier.weight(1f)) // Empurra o botão para baixo

            RoundedButton(
                text = "Criar conta",
                onClick = {
                    authViewModel.signUp {
                        onSignUpSuccess()
                    }
                },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}