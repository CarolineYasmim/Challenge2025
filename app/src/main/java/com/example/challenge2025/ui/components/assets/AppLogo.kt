package com.example.challenge2025.ui.components.assets

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.challenge2025.R

@SuppressLint("LocalContextResourcesRead")
@Composable
fun AppLogo(modifier: Modifier = Modifier) {
        Icon(
            painter = painterResource(id = R.drawable.logo_sori),
            contentDescription = "App Logo",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = modifier
        )
}