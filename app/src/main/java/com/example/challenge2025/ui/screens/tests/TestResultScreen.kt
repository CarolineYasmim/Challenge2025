package com.example.challenge2025.ui.screens.tests

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challenge2025.R
import com.example.challenge2025.model.user.UserTestResult
import com.example.challenge2025.ui.components.tests.ContinueButton
import com.example.challenge2025.ui.components.tests.UserResultCard

@Composable
fun TestResultScreen(
    result: UserTestResult,
    timeSpentMillis: Long,
    onSupportClick: () -> Unit,
    onMoreInfoClick: () -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        bottomBar = {
            // botão fixo na parte inferior
            ContinueButton(
                text = "Continuar",
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                // aplicar padding do scaffold (innerPadding) e systemBars
                .padding(innerPadding)
                .padding(WindowInsets.systemBars.asPaddingValues())
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()), // torna o conteúdo rolável
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // Imagem (sua imagem no drawable)
            Image(
                painter = painterResource(id = R.drawable.result),
                contentDescription = "Resultado do teste",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Card reutilizável com interpretação, recomendações e ações
            UserResultCard(
                result = result,
                onSupportClick = onSupportClick,
                onMoreInfoClick = onMoreInfoClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Tempo gasto: ${formatTime(timeSpentMillis)}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp)) // espaço extra para garantir scroll antes do botão fixo
        }
    }
}

// mm:ss
fun formatTime(timeMillis: Long): String {
    val seconds = (timeMillis / 1000) % 60
    val minutes = (timeMillis / 1000) / 60
    return "%02d:%02d".format(minutes, seconds)
}
