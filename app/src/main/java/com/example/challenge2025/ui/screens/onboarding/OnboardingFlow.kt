package com.example.challenge2025.ui.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.challenge2025.viewmodel.AuthViewModel
import com.example.challenge2025.viewmodel.UserViewModel


@Composable
fun OnboardingFlow(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val totalSteps = 8
    val state by userViewModel.onboardingState.collectAsState()

    AnimatedContent(
        targetState = state.currentStep,
        transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith
                    fadeOut(animationSpec = tween(durationMillis = 300))
        },
        label = "onboarding_flow_animation"
    ) { step ->
        when (step) {
            1 -> MotivationScreen(navController, userViewModel, totalSteps)
            2 -> PersonalScreen(navController, userViewModel, totalSteps)
            3 -> PotentialScreen(navController, userViewModel, totalSteps)
            4 -> CompanyInfoScreen(navController, userViewModel, totalSteps)
            5 -> CompanyDetailsScreen(navController, userViewModel, totalSteps)
            6 -> FinalScreen(navController, authViewModel)
            else -> {}
        }
    }
}