// UI/Screens/HomeScreen.kt
package com.example.challenge2025.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.challenge2025.model.data.MockData
import com.example.challenge2025.ui.components.Header
import com.example.challenge2025.ui.components.WeeklyCalendar
import com.example.challenge2025.viewmodel.CalendarViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    calendarViewModel: CalendarViewModel = viewModel()
) {
    val selectedDate = calendarViewModel.selectedDate.collectAsState()
    val currentWeek = calendarViewModel.currentWeek.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Header(
            title = "Olá ${MockData.currentUser.name}",
            user = MockData.currentUser,
            modifier = Modifier.padding(16.dp)
        )

        WeeklyCalendar(
            week = currentWeek.value,
            selectedDate = selectedDate.value,
            onDateSelected = { date ->
                calendarViewModel.selectDate(date)
                // Aqui você pode chamar uma função para carregar os dados do backend para a data selecionada
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Resto do conteúdo da HomeScreen (a ser implementado)
    }
}