package com.example.challenge2025.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.challenge2025.R
import com.example.challenge2025.ui.components.auth.BigTitle
import com.example.challenge2025.ui.components.auth.RoundedButton

@Composable
fun WelcomeScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_sori),
                contentDescription = "Personagem Sori, sua assistente pessoal",
                modifier = Modifier.size(240.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            BigTitle(text = "Vamos completar seu cadastro!")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Olá! Você já criou sua conta. Eu sou a Sori e estou aqui para te ajudar a completar seu cadastro com algumas informações importantes, para que possamos preparar uma jornada personalizada só para você. Vamos lá?",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        RoundedButton(
            text = "Estou pronto!",
            onClick = {
                navController.navigate("onboarding_flow")
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}