package com.example.challenge2025.ui.screens.tests

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challenge2025.R
import com.example.challenge2025.domain.model.tests.TestCategory
import com.example.challenge2025.domain.model.tests.TestItem
import com.example.challenge2025.domain.util.Resource
import com.example.challenge2025.ui.components.assets.Header
import com.example.challenge2025.ui.components.tests.BannerCarousel
import com.example.challenge2025.ui.components.tests.TestListContainer
import com.example.challenge2025.ui.viewmodel.TestViewModel
import com.example.challenge2025.ui.viewmodel.UserViewModel

@Composable
fun    TestsScreen(
    onTestClick: (TestItem) -> Unit,
    userViewModel: UserViewModel = hiltViewModel(),
    testViewModel: TestViewModel = hiltViewModel()
) {
    val currentUser by userViewModel.currentUser.collectAsState()
    val testsState by testViewModel.testsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Header(title = "Testes", user = currentUser)
        BannerCarousel(bannerImages = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
        ))

        Spacer(modifier = Modifier.height(24.dp))

        when (val state = testsState) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                val allTests = state.data ?: emptyList()
                val userTests = allTests.filter { it.category == TestCategory.USER }
                val companyTests = allTests.filter { it.category == TestCategory.COMPANY }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    if (userTests.isNotEmpty()) {
                        TestListContainer(
                            title = "Para você",
                            tests = userTests,
                            onTestClick = onTestClick
                        )
                    }
                    if (companyTests.isNotEmpty()) {
                        TestListContainer(
                            title = "Da empresa para o seu cuidado",
                            tests = companyTests,
                            onTestClick = onTestClick
                        )
                    }
                }
            }
            is Resource.Error -> {
                Text(text = state.message ?: "Erro ao carregar os testes.")
            }
        }
    }
}