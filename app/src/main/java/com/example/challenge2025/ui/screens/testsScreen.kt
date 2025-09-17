package com.example.challenge2025.ui.screens

import com.example.challenge2025.ui.components.BannerCarousel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.challenge2025.R
import com.example.challenge2025.ui.components.Header

@Composable
fun    HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Header(title = "Testes", avatarRes = R.drawable.avatar)
        BannerCarousel(bannerImages = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
        ))
    }

}
