package com.example.challenge2025.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challenge2025.domain.model.features.FeaturedResource
import com.example.challenge2025.domain.model.features.QuickAction
import com.example.challenge2025.domain.model.features.QuickActionType

@Composable
fun SupportComponent(
    featuredResource: FeaturedResource,
    quickActions: List<QuickAction>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Seu Espaço de Apoio",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp, bottom = 16.dp)
        )

        // Card de Destaque
        FeaturedResourceCard(resource = featuredResource, onClick = { /* Ação do botão */ })

        Spacer(modifier = Modifier.height(16.dp))

        // Ações Rápidas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            quickActions.forEach { action ->
                QuickActionItem(action = action, onClick = { /* Ação do item */ })
            }
        }
    }
}

@Composable
private fun FeaturedResourceCard(
    resource: FeaturedResource,
    onClick: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            Color.Transparent
        )
    )
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(gradient)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = resource.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resource.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = resource.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = resource.buttonText)
            }
        }
    }
}

@Composable
private fun QuickActionItem(
    action: QuickAction,
    onClick: () -> Unit
) {
    val containerColor = if (action.type == QuickActionType.URGENT) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    }
    val contentColor = if (action.type == QuickActionType.URGENT) {
        MaterialTheme.colorScheme.onTertiaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = action.icon,
            contentDescription = action.title,
            tint = contentColor,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = action.title,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            color = contentColor,
            lineHeight = 14.sp
        )
    }
}