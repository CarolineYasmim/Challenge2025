package com.example.challenge2025.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challenge2025.model.checkin.CareDaysGoal
import com.example.challenge2025.model.checkin.MentalHealthSummary
import com.example.challenge2025.ui.theme.Green

@Composable
fun WeeklyGoals(
    mentalHealth: MentalHealthSummary,
    careGoal: CareDaysGoal,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Metas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp, bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card 1: Saúde da Mente
            GoalCard(modifier = Modifier.weight(1f)) {
                GoalHeader(
                    title = mentalHealth.title,
                    icon = mentalHealth.icon,
                    value = "${mentalHealth.healthPercentage}%"
                )
                Spacer(modifier = Modifier.height(16.dp))
                GoalDetails(
                    detail1 = {
                        GoalInfoRow(
                            icon = Icons.Rounded.ArrowUpward,
                            text = "${mentalHealth.positiveEmotions} emoções positivas",
                            color = Green
                        )
                    },
                    detail2 = {
                        GoalInfoRow(
                            icon = Icons.Rounded.ArrowDownward,
                            text = "${mentalHealth.negativeEmotions} emoções negativas",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                val totalEmotions = (mentalHealth.positiveEmotions + mentalHealth.negativeEmotions).toFloat()
                val progress = if (totalEmotions > 0) mentalHealth.positiveEmotions / totalEmotions else 0f
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Green,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            // Card 2: Dias de Cuidado
            GoalCard(modifier = Modifier.weight(1f)) {
                GoalHeader(
                    title = careGoal.title,
                    icon = careGoal.icon,
                    value = "${careGoal.completedDays}/${careGoal.goalDays}"
                )
                Spacer(modifier = Modifier.height(16.dp))
                GoalDetails(
                    detail1 = {
                        Text(text = "${careGoal.completedDays} dias concluídos", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    },
                    detail2 = {
                        Text(text = "Meta semanal de ${careGoal.goalDays} dias", fontSize = 12.sp, color = LocalContentColor.current.copy(alpha = 0.7f))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                val progress = if (careGoal.goalDays > 0) careGoal.completedDays.toFloat() / careGoal.goalDays.toFloat() else 0f
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
        }
    }
}

// Container genérico para os cards de meta
@Composable
private fun GoalCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

// Cabeçalho dos cards (título, ícone, valor)
@Composable
private fun GoalHeader(
    title: String,
    icon: ImageVector,
    value: String
) {
    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    Spacer(modifier = Modifier.height(8.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

// Detalhes dos cards (as duas linhas de texto)
@Composable
private fun GoalDetails(
    detail1: @Composable () -> Unit,
    detail2: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        detail1()
        detail2()
    }
}

// Linha de informação com ícone para o card de Saúde da Mente
@Composable
private fun GoalInfoRow(icon: ImageVector, text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = color
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, fontSize = 12.sp)
    }
}