// UI/Components/FeelingIcons.kt
package com.example.challenge2025.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.MoodBad
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.Spa
import androidx.compose.ui.graphics.vector.ImageVector

object FeelingIcons {
    // Usando ícones do Material Icons como placeholder
    val Calmo: ImageVector = Icons.Default.Spa
    val Ansioso: ImageVector = Icons.Default.MoodBad
    val Feliz: ImageVector = Icons.Default.Mood
    val Triste: ImageVector = Icons.Default.SentimentDissatisfied
    val Energizado: ImageVector = Icons.Default.FlashOn
    val Cansado: ImageVector = Icons.Default.Bedtime
}