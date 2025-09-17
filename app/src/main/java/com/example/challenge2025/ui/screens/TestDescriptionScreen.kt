package com.example.challenge2025.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.challenge2025.R
import com.example.challenge2025.model.TestDetail
import com.example.challenge2025.ui.components.ContinueButton
import com.example.challenge2025.ui.components.Header

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
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Header(title = testDetail.name, avatarRes = R.drawable.avatar, onBackClick = onExit)

        Spacer(modifier = Modifier.height(16.dp))


        Image(
            painter = painterResource(id = testDetail.imageRes),
            contentDescription = testDetail.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = testDetail.description,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Perguntas: ${testDetail.totalQuestions}  •  Duração: ${testDetail.durationMinutes} min",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(32.dp))


        ContinueButton(
            text = "Iniciar teste",
            onClick = onStartTest
        )
    }
}
