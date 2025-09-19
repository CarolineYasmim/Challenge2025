package com.example.challenge2025.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.MaterialTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BannerCarousel(
    bannerImages: List<Int>
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { bannerImages.size }
    )

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % bannerImages.size
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    val indicatorColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            pageSpacing = 16.dp,
        ) { page ->
            Image(
                painter = painterResource(id = bannerImages[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            repeat(bannerImages.size) { index ->
                val isSelected = pagerState.currentPage == index

                Box(
                    modifier = Modifier
                        .size(if (isSelected) 14.dp else 12.dp)
                        .padding(4.dp)
                        .then(
                            if (isSelected) {
                                Modifier.background(indicatorColor, CircleShape)
                            } else {
                                Modifier.border(
                                    width = 2.dp,
                                    color = indicatorColor,
                                    shape = CircleShape
                                )
                            }
                        )
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                )
            }
        }

    }
}
