package com.example.challenge2025.ui.screens.tests

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.challenge2025.model.tests.TestDetail
import com.example.challenge2025.ui.components.assets.BackButton
import com.example.challenge2025.ui.components.tests.ContinueButton

@Composable
fun TestDescriptionScreen(
    testId: String,
    onStartTest: () -> Unit,
    onExit: () -> Unit
) {
    val testDetail = TestDetail.fromTestId(testId) ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {

        BackButton(onClick = onExit)

        Spacer(modifier = Modifier.height(16.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painter = painterResource(id = testDetail.imageRes),
                contentDescription = testDetail.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(
                text = testDetail.name,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = testDetail.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Perguntas: ${testDetail.totalQuestions}  •  Duração: ${testDetail.durationMinutes} min",
                style = MaterialTheme.typography.bodySmall
            )
        }


        ContinueButton(
            text = "Iniciar teste",
            onClick = onStartTest,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
}
