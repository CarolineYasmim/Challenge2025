package com.example.challenge2025.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.challenge2025.viewmodel.AuthViewModel

@Composable
fun FinalScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.sori_congratulation), // Lembre-se de adicionar este drawable
                contentDescription = "Sori parabenizando o usuário",
                modifier = Modifier
                    // --- IMAGEM AUMENTADA ---
                    .fillMaxWidth() // Ocupa a largura
                    .height(450.dp)   // Altura ajustada para a proporção
            )
            Spacer(modifier = Modifier.height(40.dp))

            BigTitle(text = "Tudo pronto!")
            Spacer(modifier = Modifier.height(16.dp))

            // --- TEXTO CORRIGIDO PARA O CONTEXTO ---
            Text(
                text = "Sua jornada de autoconhecimento começa agora. Estamos aqui para te apoiar em cada passo.",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        RoundedButton(
            text = "Começar a usar", // Texto do botão mais convidativo
            onClick = {
                authViewModel.signUp {
                    navController.navigate("home") {
                        popUpTo("onboarding_route") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}