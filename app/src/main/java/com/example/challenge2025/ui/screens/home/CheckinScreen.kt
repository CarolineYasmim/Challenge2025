@file:Suppress("DEPRECATION")

package com.example.challenge2025.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.challenge2025.model.checkin.Checkin
import com.example.challenge2025.model.user.Feeling
import com.example.challenge2025.viewmodel.CheckinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckinScreen(
    date: LocalDate,
    viewModel: CheckinViewModel,
    onClose: () -> Unit,
    onSubmit: () -> Unit
) {
    val selectedFeelings by viewModel.selectedFeelings.collectAsState()
    val checkinNotes by viewModel.checkinNotes.collectAsState()
    val availableFeelings = Checkin.availableFeelings

    val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy", Locale("pt", "BR"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seu bem-estar hoje") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título mais acolhedor
            Text(
                text = "Como você está se sentindo hoje?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
            // Subtítulo com a data
            Text(
                text = "Check-in de ${date.format(formatter)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

            FeelingGrid(
                feelings = availableFeelings,
                selectedFeelings = selectedFeelings,
                onFeelingToggle = { feeling ->
                    viewModel.toggleFeeling(feeling)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Adicionar uma anotação (opcional)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = checkinNotes,
                onValueChange = { viewModel.updateNotes(it) },
                placeholder = { Text("Escreva sobre seu dia, gratidão, etc.") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.submitCheckin(date)
                    onSubmit()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = selectedFeelings.isNotEmpty()
            ) {
                Text("Salvar Check-in", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FeelingGrid(
    feelings: List<Feeling>,
    selectedFeelings: List<Feeling>,
    onFeelingToggle: (Feeling) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 90.dp),
        modifier = Modifier.heightIn(max = 350.dp), // Evita que a grid ocupe a tela toda
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(feelings) { feeling ->
            FeelingItem(
                feeling = feeling,
                isSelected = selectedFeelings.contains(feeling),
                onClick = { onFeelingToggle(feeling) }
            )
        }
    }
}

@Composable
private fun FeelingItem(
    feeling: Feeling,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = Color(feeling.colorHex.toColorInt())
    val backgroundColor = if (isSelected) color.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    val borderColor = if (isSelected) color else Color.Transparent
    val contentColor = if (isSelected) color else MaterialTheme.colorScheme.onSurface

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Surface(
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = backgroundColor,
            border = BorderStroke(2.dp, borderColor)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = feeling.iconRes),
                    contentDescription = feeling.name,
                    tint = contentColor,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = feeling.name,
            color = contentColor,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}