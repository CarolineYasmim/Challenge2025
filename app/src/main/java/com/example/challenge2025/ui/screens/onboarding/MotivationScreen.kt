package com.example.challenge2025.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.challenge2025.R
import com.example.challenge2025.ui.components.auth.RoundedButton
import com.example.challenge2025.ui.components.onboarding.SelectableCard
import com.example.challenge2025.ui.components.onboarding.StepLayout
import com.example.challenge2025.viewmodel.UserViewModel

@Composable
fun MotivationScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    totalSteps: Int
) {
    val state by userViewModel.onboardingState.collectAsState()
    val currentStep = 1

    StepLayout(
        navController = navController,
        viewModel = userViewModel,
        currentStep = currentStep,
        totalSteps = totalSteps,
        title = "O que te motiva a buscar apoio",
        subtitle = "Selecione as opções que mais se alinham aos seus objetivos. Você pode escolher mais de uma!"
    ) {
        // A Column interna agora preenche a altura máxima disponível pelo AuthScreenLayout
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                // Lembre-se de adicionar a imagem 'think_sori' à pasta drawable
                painter = painterResource(id = R.drawable.think_sori),
                contentDescription = "Sori pensando",
                modifier = Modifier
                    // Aumentando o tamanho da Sori
                    .size(150.dp)
            )

            // A LazyColumn agora tem um 'weight' para ocupar o espaço entre a imagem e o botão
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(userViewModel.motivationOptions) { option ->
                    SelectableCard(
                        text = option,
                        isSelected = state.selectedMotivations.contains(option),
                        onCardClick = { userViewModel.toggleMotivationSelection(option) }
                    )
                }
            }

            // O botão "Próximo" fica na parte inferior
            RoundedButton(
                text = "Próximo",
                onClick = { userViewModel.nextStep() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}