package com.example.challenge2025.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
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
import com.example.challenge2025.model.data.MockData.currentUser
import com.example.challenge2025.model.data.MockHomeData
import com.example.challenge2025.ui.components.CheckinHistory
import com.example.challenge2025.ui.components.Header
import com.example.challenge2025.ui.components.SupportComponent
import com.example.challenge2025.ui.components.WeeklyCalendar
import com.example.challenge2025.ui.components.WeeklyGoals
import com.example.challenge2025.viewmodel.CalendarViewModel
import com.example.challenge2025.viewmodel.UserViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    calendarViewModel: CalendarViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel()
) {
    val selectedDate = calendarViewModel.selectedDate.collectAsState()
    val currentWeek = calendarViewModel.currentWeek.collectAsState()

    // AJUSTE: O padding foi movido para esta Column principal.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            // 1. Padding de 16.dp em todos os lados da tela.
            .padding(16.dp),
        // 2. Espaçamento vertical de 24.dp aplicado entre cada componente.
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // AJUSTE: Modifiers de padding foram removidos dos componentes filhos.
        Header(
            title = "Olá ${currentUser.name}",
            user = currentUser
        )

        WeeklyCalendar(
            week = currentWeek.value,
            selectedDate = selectedDate.value,
            onDateSelected = { date ->
                calendarViewModel.selectDate(date)
                // Aqui você pode chamar uma função para carregar os dados do backend para a data selecionada
            }
        )

        CheckinHistory(
            selectedDate = selectedDate.value, // .value para passar LocalDate
            onCheckinClick = {
                // Navegar para a tela de check-in com a data selecionada
                navController.navigate("checkin/${selectedDate.value}")
            }
        )

        WeeklyGoals(
            mentalHealth = MockHomeData.mentalHealthSummary,
            careGoal = MockHomeData.careDaysGoal
        )

        SupportComponent(
            featuredResource = MockHomeData.featuredResource,
            quickActions = MockHomeData.quickActions
        )


    }
}