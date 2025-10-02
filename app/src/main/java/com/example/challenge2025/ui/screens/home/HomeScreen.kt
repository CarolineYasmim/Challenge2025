package com.example.challenge2025.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.challenge2025.domain.util.Resource
import com.example.challenge2025.ui.components.assets.Header
import com.example.challenge2025.ui.components.home.CheckinHistory
import com.example.challenge2025.ui.components.home.WeeklyCalendar
import com.example.challenge2025.ui.components.home.WeeklyGoals
import com.example.challenge2025.ui.viewmodel.home.CalendarViewModel
import com.example.challenge2025.ui.viewmodel.user.UserViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    // Coletando os estados necessários
    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val currentWeek by calendarViewModel.currentWeek.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()
    val weekCheckins by calendarViewModel.weekCheckins.collectAsState()
    val statisticsState by calendarViewModel.statistics.collectAsState()
    val checkinForSelectedDate = weekCheckins[selectedDate]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Header(
            title = "Olá, ${currentUser.name}",
            user = currentUser
        )

        WeeklyCalendar(
            week = currentWeek,
            selectedDate = selectedDate,
            onDateSelected = { date ->
                calendarViewModel.selectDate(date)
            }
        )

        // Passa o dado real (ou nulo) para o CheckinHistory
        CheckinHistory(
            selectedDate = selectedDate,
            checkin = checkinForSelectedDate,
            onCheckinClick = {
                navController.navigate("checkin/${selectedDate}")
            }
        )

        when (val state = statisticsState) {
            is Resource.Loading -> {
                // Mostra um indicador de carregamento enquanto busca as estatísticas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                // Passa os dados para o componente WeeklyGoals
                WeeklyGoals(statistics = state.data)
            }
            is Resource.Error -> {
                // Mostra uma mensagem de erro
                Text(text = state.message ?: "Erro ao carregar metas.")
            }
        }
    }
}