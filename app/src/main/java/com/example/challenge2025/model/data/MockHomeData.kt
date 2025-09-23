package com.example.challenge2025.model.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.VerifiedUser
import com.example.challenge2025.model.checkin.CareDaysGoal
import com.example.challenge2025.model.checkin.MentalHealthSummary


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
}