package com.example.challenge2025.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
        subtitle = "Digite seus dados para fazer cadastro na plataforma"
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AuthTextField(
                    value = state.name,
                    onValueChange = authViewModel::onNameChange,
                    label = "Seu nome ou apelido",
                    isError = state.nameError.isNotEmpty(),
                    errorMessage = state.nameError
                )

                AuthTextField(
                    value = state.email,
                    onValueChange = authViewModel::onEmailChange,
                    label = "Seu e-mail",
                    isError = state.emailError.isNotEmpty(),
                    errorMessage = state.emailError,
                    keyboardType = KeyboardType.Email
                )

                AuthTextField(
                    value = state.password,
                    onValueChange = authViewModel::onPasswordChange,
                    label = "Crie uma senha",
                    isError = state.passwordError.isNotEmpty(),
                    errorMessage = state.passwordError,
                    keyboardType = KeyboardType.Password
                )
            }

            RoundedButton(
                text = "Criar conta",
                onClick = {
                    authViewModel.signUp {
                        onSignUpSuccess()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}