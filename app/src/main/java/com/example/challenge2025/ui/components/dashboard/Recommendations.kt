package com.example.challenge2025.ui.components.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
// MUDANÇA: Garantir que o import aponta para o modelo de domínio correto
import com.example.challenge2025.domain.model.dashboard.Recommendation

@Composable
fun Recommendations(recommendations: List<Recommendation>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Recomendações para Você",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recommendations) { recommendation ->
                RecommendationCard(recommendation)
            }
        }
    }
}

@Composable
private fun RecommendationCard(recommendation: Recommendation) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(180.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = recommendation.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = recommendation.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.heightIn(min = 40.dp)
            )
            Spacer(Modifier.weight(1f))

            TextButton(onClick = { /*TODO*/ }) {
                Text(recommendation.actionText)
                Spacer(Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}