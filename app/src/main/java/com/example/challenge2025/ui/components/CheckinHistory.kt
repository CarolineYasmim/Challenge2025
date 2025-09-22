package com.example.challenge2025.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challenge2025.model.data.MockCheckinData
import com.example.challenge2025.model.user.CheckinStatus
import com.example.challenge2025.model.user.UserCheckin
import java.time.LocalDate
import androidx.core.graphics.toColorInt

@Composable
fun CheckinHistory(
    selectedDate: LocalDate,
    onCheckinClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val checkinStatus = MockCheckinData.getCheckinStatus(selectedDate)
    val checkin = MockCheckinData.getCheckinForDate(selectedDate)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        shadowElevation = 4.dp
    ) {
        AnimatedContent(
            targetState = checkinStatus,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
            },
            label = "checkin_history_transition"
        ) { status ->
            when (status) {
                CheckinStatus.NOT_DONE -> NoCheckinView(
                    selectedDate = selectedDate,
                    onCheckinClick = onCheckinClick
                )

                CheckinStatus.COMPLETED -> checkin?.let {
                    CheckinSummaryView(
                        checkin = it,
                        onCheckinClick = onCheckinClick
                    )
                }
            }
        }
    }
}

@Composable
private fun NoCheckinView(
    selectedDate: LocalDate,
    onCheckinClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                        )
                    )
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.SentimentNeutral,
                contentDescription = "Check-in pendente",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (selectedDate == LocalDate.now()) {
                "Como você está se sentindo hoje?"
            } else {
                "Como você estava se sentindo nesse dia?"
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Compartilhar seus sentimentos ajuda no seu autocuidado",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onCheckinClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Fazer Check-in",
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@SuppressLint("UseKtx")
@Composable
private fun CheckinSummaryView(
    checkin: UserCheckin,
    onCheckinClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clickable { onCheckinClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Converte hex colors para Color objects
        val feelingColors = checkin.feelings.map { feeling ->
            Color(feeling.colorHex.toColorInt())
        }

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .drawBehind {
                    if (feelingColors.isNotEmpty()) {
                        val sweepGradient = Brush.sweepGradient(
                            feelingColors + feelingColors.first()
                        )
                        drawCircle(
                            brush = sweepGradient,
                            radius = size.width / 2 - 4.dp.toPx(),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(
                                width = 8.dp.toPx()
                            )
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            val primaryFeeling = checkin.feelings.firstOrNull()
            if (primaryFeeling != null) {
                Icon(
                    painter = painterResource(id = primaryFeeling.iconRes),
                    contentDescription = primaryFeeling.name,
                    modifier = Modifier.size(40.dp),
                    tint = Color(android.graphics.Color.parseColor(primaryFeeling.colorHex))
                )
            } else {
                Icon(
                    imageVector = Icons.Default.SentimentSatisfied,
                    contentDescription = "Sentimento",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = generateSummaryText(checkin),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            checkin.feelings.forEach { feeling ->
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color(android.graphics.Color.parseColor(feeling.colorHex)))
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        Text(
            text = "Fazer novamente",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )

        checkin.notes?.let { notes ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "\"$notes\"",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                maxLines = 2
            )
        }
    }
}

private fun generateSummaryText(checkin: UserCheckin): String {
    return when (checkin.feelings.size) {
        1 -> "Você estava se sentindo ${checkin.feelings.first().name.lowercase()}"
        2 -> "Você estava se sentindo ${checkin.feelings[0].name.lowercase()} e ${checkin.feelings[1].name.lowercase()}"
        else -> {
            val mainFeelings = checkin.feelings.take(2).joinToString(" e ") { it.name.lowercase() }
            "Você estava se sentindo $mainFeelings"
        }
    }
}
