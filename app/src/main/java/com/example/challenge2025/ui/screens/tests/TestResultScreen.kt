package com.example.challenge2025.ui.screens.tests

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challenge2025.R
import com.example.challenge2025.domain.util.Resource
import com.example.challenge2025.ui.components.tests.ContinueButton
import com.example.challenge2025.ui.components.tests.UserResultCard
import com.example.challenge2025.ui.viewmodel.test.TestInProgressViewModel

@Composable
fun TestResultScreen(
    onContinue: () -> Unit,
    viewModel: TestInProgressViewModel = hiltViewModel()
) {
    val finalResultState by viewModel.finalResultState.collectAsState()

    Scaffold(
        bottomBar = {
            ContinueButton(
                text = "Continuar",
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    ) { innerPadding ->
        when (val state = finalResultState) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message ?: "Erro ao processar o resultado.")
                }
            }
            is Resource.Success -> {
                val resultDomain = state.data
                if (resultDomain != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        HeaderImage()
                        Spacer(modifier = Modifier.height(20.dp))
                        UserResultCard(
                            result = resultDomain,
                            onSupportClick = { /* TODO */ },
                            onMoreInfoClick = { /* TODO */ }
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderImage() {
    Image(
        painter = painterResource(id = R.drawable.result),
        contentDescription = "Resultado do teste",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    )
}