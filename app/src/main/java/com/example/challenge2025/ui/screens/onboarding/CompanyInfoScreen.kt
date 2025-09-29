@file:Suppress("DEPRECATION")

package com.example.challenge2025.ui.screens.onboarding

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.AutoGraph
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.challenge2025.ui.components.auth.RoundedButton
import com.example.challenge2025.ui.components.onboarding.StepLayout
import com.example.challenge2025.ui.theme.Blue
import com.example.challenge2025.viewmodel.UserViewModel

@Composable
fun CompanyInfoScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    totalSteps: Int
) {
    val state by userViewModel.onboardingState.collectAsState()
    // O passo foi corrigido para 5, de acordo com o OnboardingFlow
    val currentStep = 5

    StepLayout(
        navController = navController,
        viewModel = userViewModel,
        currentStep = currentStep,
        totalSteps = totalSteps,
        title = "Ajude a melhorar seu ambiente",
        subtitle = "Ao fornecer dados anônimos, você nos ajuda a criar um ambiente de trabalho mais saudável para todos."
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                InfoCard(
                    icon = Icons.Rounded.AdminPanelSettings,
                    text = "Seus dados são 100% anônimos e confidenciais."
                )
                InfoCard(
                    icon = Icons.Rounded.AutoGraph,
                    text = "Geramos relatórios para que sua empresa possa agir."
                )
                InfoCard(
                    icon = Icons.Rounded.Groups,
                    text = "Você contribui para o bem-estar de seus colegas."
                )
            }

            // --- SEÇÃO DO CHECKBOX E BOTÃO ---
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TermsAndConditionsCheckbox(
                    checked = state.termsAccepted,
                    onCheckedChange = userViewModel::onTermsAcceptedChange
                )
                Spacer(modifier = Modifier.height(16.dp))
                RoundedButton(
                    text = "Entendi",
                    onClick = { userViewModel.nextStep() },
                    // O botão agora depende da aceitação dos termos
                    enabled = state.termsAccepted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoCard(icon: ImageVector, text: String) {
    // O código do InfoCard permanece o mesmo
    val isDarkTheme = isSystemInDarkTheme()

    val containerColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.surface
    }
    val cardElevation = if (isDarkTheme) {
        CardDefaults.cardElevation(defaultElevation = 4.dp)
    } else {
        CardDefaults.cardElevation(defaultElevation = 0.dp)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = cardElevation
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// --- COMPONENTE DO CHECKBOX ---
@Composable
private fun TermsAndConditionsCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(8.dp))

        val annotatedString = buildAnnotatedString {
            append("Eu li e aceito os ")
            pushStringAnnotation(tag = "TERMS", annotation = "terms_route")
            withStyle(style = SpanStyle(color = Blue, fontWeight = FontWeight.Bold)) {
                append("Termos de Uso")
            }
            pop()
            append(" e a ")
            pushStringAnnotation(tag = "PRIVACY", annotation = "privacy_route")
            withStyle(style = SpanStyle(color = Blue, fontWeight = FontWeight.Bold)) {
                append("Política de Privacidade")
            }
            pop()
            append(".")
        }

        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            onClick = { offset ->
                // TODO: Adicionar navegação para as telas de Termos e Privacidade
            }
        )
    }
}