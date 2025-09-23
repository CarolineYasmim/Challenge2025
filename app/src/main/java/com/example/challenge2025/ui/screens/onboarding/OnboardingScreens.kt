package com.example.challenge2025.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.challenge2025.R
import com.example.challenge2025.ui.components.AppLogo
import com.example.challenge2025.ui.components.BigTitle
import com.example.challenge2025.ui.components.OnboardingPagination
import com.example.challenge2025.ui.components.RoundedButton

@Composable
fun OnboardingScreen1(navController: NavController) {
    OnboardingPageLayout(
        imageRes = R.drawable.woman_smiley,
        pageNumber = 1,
        title = "Olá!",
        description = "Aqui você encontra um espaço seguro e acolhedor para acompanhar como se sente, reconhecer sinais de estresse e receber apoio constante.\n\nQueremos ajudar você a se sentir mais forte a cada dia e lembrar que sua saúde emocional importa.",
        buttonText = "Começar",
        onButtonClick = { navController.navigate("onboarding2") }
    )
}

@Composable
fun OnboardingScreen2(navController: NavController) {
    OnboardingPageLayout(
        imageRes = R.drawable.sad_woman_w_emojis,
        pageNumber = 2,
        titleComponent = { BigTitle(modifier = Modifier.padding(top = 26.dp), text = "Ferramenta para cuidar\nde você") },
        description = "Realize testes rápidos de ansiedade, estresse e bem-estar. Receba motivações diárias e acompanhe sua evolução emocional.",
        buttonText = "Próximo",
        onButtonClick = { navController.navigate("onboarding3") }
    )
}

@Composable
fun OnboardingScreen3(navController: NavController) {
    OnboardingPageLayout(
        imageRes = R.drawable.woman_w_emojis,
        pageNumber = 3,
        titleComponent = { BigTitle(modifier = Modifier.padding(top = 26.dp), text = "Conheça a si mesmo") },
        description = "Visualize padrões emocionais, entenda como se sente ao longo do tempo e previna riscos com mais consciência e equilíbrio.",
        buttonText = "Entrar no App",
        onButtonClick = {
            navController.navigate("login") {
                // Limpa a pilha de navegação parszdsa que o usuário não possa voltar ao onboarding
                popUpTo("onboarding1") { inclusive = true }
            }
        }
    )
}

// Layout base reutilizável para evitar repetição de código
@Composable
private fun OnboardingPageLayout(
    imageRes: Int,
    pageNumber: Int,
    title: String? = null,
    titleComponent: @Composable (() -> Unit)? = null,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 60.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (title != null) {
                BigTitle(text = title)
            }

            if (pageNumber > 1) {
                AppLogo(modifier = Modifier.height(40.dp))
            }

            if (titleComponent != null) {
                titleComponent()
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = description,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 34.dp)
            )

            Spacer(modifier = Modifier.weight(1f)) // Empurra o conteúdo abaixo para o fundo

            OnboardingPagination(currentPage = pageNumber)

            Spacer(modifier = Modifier.height(24.dp))

            RoundedButton(text = buttonText, onClick = onButtonClick)
        }
    }
}