package com.example.challenge2025.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.challenge2025.ui.components.auth.AuthTextField
import com.example.challenge2025.ui.components.auth.RoundedButton
import com.example.challenge2025.ui.components.onboarding.GenderButtons
import com.example.challenge2025.ui.components.onboarding.StepLayout
import com.example.challenge2025.ui.viewmodel.UserViewModel

@Composable
fun PersonalScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    totalSteps: Int
) {
    val state by userViewModel.onboardingState.collectAsState()
    val currentStep = 2

    StepLayout(
        navController = navController,
        viewModel = userViewModel,
        currentStep = currentStep,
        totalSteps = totalSteps,
        title = "Sobre você",
        subtitle = "Essas informações nos ajudam a personalizar sua experiência."
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // --- SELETOR DE IDADE SUBSTITUÍDO POR AuthTextField ---
                Text(
                    text = "Qual é a sua idade?",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                AuthTextField(
                    value = state.age,
                    onValueChange = userViewModel::onAgeChange,
                    label = "Sua idade",
                    keyboardType = KeyboardType.Number, // Teclado numérico
                    isError = state.ageError != null,
                    errorMessage = state.ageError
                )
                // ----------------------------------------------------

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Com qual gênero você se identifica?",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                GenderButtons(
                    genderOptions = userViewModel.genderOptions,
                    selectedGender = state.gender,
                    onGenderSelected = userViewModel::onGenderChange
                )
            }

            RoundedButton(
                text = "Próximo",
                onClick = { userViewModel.nextStep() },
                // A validação agora checa se a string da idade não está vazia
                enabled = state.age.isNotBlank() && state.gender.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
            )
        }
    }
}