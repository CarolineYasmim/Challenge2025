package com.example.challenge2025.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
    userViewModel: UserViewModel
) {
    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val currentWeek by calendarViewModel.currentWeek.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Header(
            title = "Olá ${currentUser.name}",
            user = currentUser
        )

        WeeklyCalendar(
            week = currentWeek,
            selectedDate = selectedDate,
            onDateSelected = { date ->
                calendarViewModel.selectDate(date)
            }
        )

        CheckinHistory(
            selectedDate = selectedDate,
            // CORREÇÃO: O nome do parâmetro foi ajustado para 'onCheckinClick'
            onCheckinClick = {
                navController.navigate("checkin/${selectedDate}")
            }
        )

        // Nota: Se você renomeou o componente de 'WeeklyGoalsComponent' para 'WeeklyGoals',
        // apenas ajuste o nome da chamada aqui.
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