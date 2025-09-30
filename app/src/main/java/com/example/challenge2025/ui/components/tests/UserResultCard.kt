package com.example.challenge2025.ui.components.tests

//noinspection SuspiciousImport
import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserResultCard(
    result: UserTestResult,
    modifier: Modifier = Modifier,
    onSupportClick: () -> Unit,
    onMoreInfoClick: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()

    val containerColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val cardElevation = if (isDarkTheme) {
        CardDefaults.cardElevation(defaultElevation = 8.dp)
    } else {
        CardDefaults.cardElevation(defaultElevation = 0.dp)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = cardElevation
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: título e badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Seu resultado está pronto",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = result.interpretation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                // badge nível
                val levelColor = when (result.level.lowercase()) {
                    "alto" -> Color(0xFFD32F2F)
                    "moderado" -> Color(0xFFFFA000)
                    else -> Color(0xFF2E7D32)
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(color = levelColor.copy(alpha = 0.12f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = result.level.uppercase(),
                        color = levelColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mensagem acolhedora
            Text(
                text = friendlyMessage(result),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Recomendações dinâmicas
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "O que você pode fazer agora",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                result.recommendations.forEach { rec ->
                    RecommendationItem(text = rec)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botões de apoio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = onSupportClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu_help),
                        contentDescription = "Apoio",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Recursos de apoio")
                }

                TextButton(onClick = onMoreInfoClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dialog_info),
                        contentDescription = "Mais info",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Mais info")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rodapé: timestamp
            val sdf = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault())
            val dateText = try { sdf.format(Date(result.timestamp)) } catch (t: Throwable) { "" }

            Text(
                text = "Resultado registrado em $dateText",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun RecommendationItem(text: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}


private fun friendlyMessage(result: UserTestResult): String {
    val encouragement = " Lembre-se: você deu um passo importante ao verificar isso. Procurar apoio é um ato de cuidado."
    return encouragement
}