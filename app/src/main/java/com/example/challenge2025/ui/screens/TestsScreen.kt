package com.example.challenge2025.ui.screens

import com.example.challenge2025.ui.components.BannerCarousel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.challenge2025.R
import com.example.challenge2025.model.MockData
import com.example.challenge2025.model.TestCategory
import com.example.challenge2025.model.TestItem
import com.example.challenge2025.model.TestStatus
import com.example.challenge2025.ui.components.Header
import com.example.challenge2025.ui.components.TestListContainer

@Composable
fun    TestsScreen(
    onTestClick: (TestItem) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Header(title = "Testes", avatarRes = R.drawable.avatar)
        BannerCarousel(bannerImages = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
        ))


        val userTests = MockData.tests.filter { it.category == TestCategory.USER }
        val companyTests = MockData.tests.filter { it.category == TestCategory.COMPANY }

        Column(modifier = Modifier.fillMaxSize()) {
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
