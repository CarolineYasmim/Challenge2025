package com.example.challenge2025.ui.screens.Tests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.challenge2025.R
import com.example.challenge2025.model.data.MockData
import com.example.challenge2025.model.tests.TestCategory
import com.example.challenge2025.model.tests.TestItem
import com.example.challenge2025.ui.components.BannerCarousel
import com.example.challenge2025.ui.components.Header
import com.example.challenge2025.ui.components.TestListContainer

@Composable
fun    TestsScreen(
    onTestClick: (TestItem) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Header(title = "Testes", user = MockData.currentUser)
        BannerCarousel(bannerImages = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
        ))


        val userTests = MockData.tests.filter { it.category == TestCategory.USER }
        val companyTests = MockData.tests.filter { it.category == TestCategory.COMPANY }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TestListContainer(
                title = "Para você",
                tests = userTests,
                onTestClick = onTestClick
            )

            TestListContainer(
                title = "Da empresa para o seu cuidado",
                tests = companyTests,
                onTestClick = onTestClick
            )
        }
    }
}
