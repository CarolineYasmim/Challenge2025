package com.example.challenge2025.ui.screens.tests

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.challenge2025.domain.util.Resource
import com.example.challenge2025.ui.components.assets.BackButton
import com.example.challenge2025.ui.viewmodel.test.TestInProgressViewModel

@Composable
fun TestQuestionScreen(
    navController: NavController,
    viewModel: TestInProgressViewModel = hiltViewModel()
) {
    val testState by viewModel.testState.collectAsState()
    val questionIndex by viewModel.currentQuestionIndex.collectAsState()
    val submitResultState by viewModel.submitResultState.collectAsState()

    // Navegação quando o resultado final chega
    LaunchedEffect(submitResultState) {
        if (submitResultState is Resource.Success) {
            val result = (submitResultState as Resource.Success).data
            navController.navigate("testResult/${result?.testId}") {
                popUpTo("testDescription/${result?.testId}") { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            BackButton(onClick = { navController.popBackStack() })
            Spacer(modifier = Modifier.height(16.dp))

            when (val state = testState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {
                    Text(text = state.message ?: "Erro ao carregar as perguntas.")
                }

                is Resource.Success -> {
                    val testDetail = state.data
                    if (testDetail != null && questionIndex < testDetail.questions.size) {
                        val question = testDetail.questions[questionIndex]

                        LinearProgressIndicator(
                            progress = { (questionIndex + 1) / testDetail.totalQuestions.toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = question.text,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(48.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            question.options.forEach { option ->
                                Button(
                                    onClick = {
                                        viewModel.selectAnswer(
                                            question = question,
                                            answerText = option.text,
                                            answerValue = option.value
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = option.text,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Overlay de envio de resultado
        when (submitResultState) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        tonalElevation = 8.dp,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(32.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Enviando suas respostas...")
                        }
                    }
                }
            }

            is Resource.Error -> {
                val errorMessage = (submitResultState as Resource.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        tonalElevation = 8.dp,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(32.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Erro ao enviar resultado", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(errorMessage ?: "Tente novamente.")
                        }
                    }
                }
            }

            else -> Unit
        }
    }
}
