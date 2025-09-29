package com.example.challenge2025.ui.components.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage

@Composable
fun ProfileImagePicker(
    modifier: Modifier = Modifier,
    selectedImageUri: String?, // Recebe a URI da imagem selecionada
    onImageClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                shape = CircleShape
            )
            .clickable { onImageClick() },
        contentAlignment = Alignment.Center
    ) {
        if (selectedImageUri != null) {
            // Se uma imagem foi selecionada, exibe-a
            AsyncImage(
                model = selectedImageUri.toUri(),
                contentDescription = "Foto de perfil selecionada",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            // Senão, exibe o ícone padrão
            Icon(
                imageVector = Icons.Rounded.AddAPhoto,
                contentDescription = "Adicionar Foto",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}