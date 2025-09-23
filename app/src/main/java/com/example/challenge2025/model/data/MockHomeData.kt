package com.example.challenge2025.model.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.Emergency
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Headset
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.VerifiedUser
import com.example.challenge2025.model.checkin.CareDaysGoal
import com.example.challenge2025.model.checkin.MentalHealthSummary
import com.example.challenge2025.model.features.FeaturedResource
import com.example.challenge2025.model.features.QuickAction
import com.example.challenge2025.model.features.QuickActionType


object MockHomeData {

    // Dados para o card "Saúde da Mente"
    val mentalHealthSummary = MentalHealthSummary(
        icon = Icons.Rounded.FavoriteBorder,
        healthPercentage = 78,
        positiveEmotions = 12,
        negativeEmotions = 4 // Total de 16 emoções, 12/16 = 75% positivo
    )

    // Dados para o card "Dias de Cuidado"
    val careDaysGoal = CareDaysGoal(
        icon = Icons.Rounded.VerifiedUser,
        completedDays = 3,
        goalDays = 5
    )

        // ... (mentalHealthSummary e careDaysGoal que já criamos)

        // Dados para o novo Componente de Apoio
        val featuredResource = FeaturedResource(
            title = "Uma pequena pausa pode transformar seu dia",
            description = "Exercícios de respiração ajudam a acalmar a mente e reduzir o estresse instantaneamente.",
            buttonText = "Respirar por 1 minuto",
            icon = Icons.Rounded.SelfImprovement
        )

        val quickActions = listOf(
            QuickAction("Agendar Conversa", Icons.Rounded.Event, QuickActionType.DEFAULT),
            QuickAction("Artigos e Dicas", Icons.AutoMirrored.Rounded.MenuBook, QuickActionType.DEFAULT),
            QuickAction("Canal de Escuta", Icons.Rounded.Headset, QuickActionType.DEFAULT),
            QuickAction("Ajuda Urgente", Icons.Rounded.Emergency, QuickActionType.URGENT)
        )
    }