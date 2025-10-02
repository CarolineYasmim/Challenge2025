package com.example.challenge2025.ui.components.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.challenge2025.ui.components.assets.BackButton
import com.example.challenge2025.ui.components.auth.AuthScreenLayout
import com.example.challenge2025.ui.viewmodel.user.UserViewModel

@Composable
fun StepLayout(
    navController: NavController,
    viewModel: UserViewModel,
    currentStep: Int,
    totalSteps: Int,
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    val progress = if (currentStep > 1) ((currentStep - 1).toFloat() / totalSteps.toFloat()) else 0f

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- MODIFICAÇÃO NA ROW SUPERIOR ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Usa espaçamento entre os itens
        ) {
            BackButton(onClick = viewModel::previousStep)

            // A barra de progresso agora ocupa todo o espaço restante
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )
        }

        AuthScreenLayout(
            title = title,
            subtitle = subtitle
        ) {
            content()
        }
    }
}