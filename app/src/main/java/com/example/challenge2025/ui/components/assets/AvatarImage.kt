package com.example.challenge2025.ui.components.assets

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.challenge2025.model.user.User

@Composable
fun AvatarImage(
    user: User,
    modifier: Modifier = Modifier
) {
    if (!user.avatarUrl.isNullOrEmpty()) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = "Avatar do usuário",
            modifier = modifier,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = user.avatarRes)
        )
    } else {
        Image(
            painter = painterResource(id = user.avatarRes),
            contentDescription = "Avatar do usuário",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}