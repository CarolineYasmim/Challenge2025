package com.example.challenge2025.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.challenge2025.ui.components.onboarding.PotentialChart
import com.example.challenge2025.ui.components.auth.RoundedButton
import com.example.challenge2025.ui.components.onboarding.StepLayout
import com.example.challenge2025.ui.viewmodel.user.UserViewModel

@Composable
fun PotentialScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    totalSteps: Int
) {
    val state by userViewModel.onboardingState.collectAsState()
    val currentStep = 3

    StepLayout(
        navController = navController,
        viewModel = userViewModel,
        currentStep = currentStep,
        totalSteps = totalSteps,
        title = "Seu Potencial de Autocuidado",
        subtitle = "Com base nas suas motivações, já podemos ver como essa jornada pode te ajudar a florescer."
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PotentialChart(
                modifier = Modifier.padding(top = 32.dp),
                motivations = state.selectedMotivations.toList()
            )

            RoundedButton(
                text = "Próximo",
                onClick = { userViewModel.nextStep() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
            )
        }
    }
}