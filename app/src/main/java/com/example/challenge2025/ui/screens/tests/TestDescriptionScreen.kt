package com.example.challenge2025.ui.screens.tests

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challenge2025.domain.util.Resource
import com.example.challenge2025.ui.components.assets.BackButton
import com.example.challenge2025.ui.components.tests.ContinueButton
import com.example.challenge2025.ui.viewmodel.test.TestDetailViewModel

@Composable
fun TestDescriptionScreen(
    onStartTest: () -> Unit,
    onExit: () -> Unit,

    viewModel: TestDetailViewModel = hiltViewModel()
) {
    val detailState by viewModel.testDetailState.collectAsState()
    val startAttemptState by viewModel.startAttemptState.collectAsState()


    LaunchedEffect(startAttemptState) {
        if (startAttemptState is Resource.Success) {
            onStartTest()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {
        BackButton(onClick = onExit)
        Spacer(modifier = Modifier.height(16.dp))

        when (val state = detailState) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message ?: "Erro ao carregar detalhes do teste.")
                }
            }
            is Resource.Success -> {
                val testDetail = state.data
                if (testDetail != null) {

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
                        onClick = { viewModel.startTestAttempt() },
                        enabled = startAttemptState !is Resource.Loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}