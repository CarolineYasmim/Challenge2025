package com.example.challenge2025.ui.components.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.challenge2025.ui.components.auth.RoundedButton

@Composable
fun GenderButtons(
    genderOptions: List<String>, // Recebe a lista de opções
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        genderOptions.forEach { gender ->
            RoundedButton(
                text = gender,
                onClick = { onGenderSelected(gender) },
                modifier = Modifier.fillMaxWidth(),
                isOutlined = selectedGender != gender
            )
        }
    }
}