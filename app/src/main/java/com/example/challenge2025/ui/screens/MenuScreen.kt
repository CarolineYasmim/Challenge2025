package com.example.challenge2025.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.challenge2025.ui.components.*
import com.example.challenge2025.viewmodel.UserViewModel

@Composable
fun MenuScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel() // Use o ViewModel
) {
    val currentUser by userViewModel.currentUser.collectAsState()

    // Listas de configurações (a lógica de clique será adicionada depois)
    val accountItems = listOf(
        SettingItem("Dados Pessoais", Icons.Rounded.Person, SettingType.NAVIGATION),
        SettingItem("Dados Corporativos", Icons.Rounded.Business, SettingType.NAVIGATION)
    )
    val preferencesItems = listOf(
        SettingItem("Notificações", Icons.Rounded.Notifications, SettingType.TOGGLE),
        SettingItem("Idioma", Icons.Rounded.Language, SettingType.NAVIGATION)
    )
    val supportItems = listOf(
        SettingItem("Central de Ajuda", Icons.AutoMirrored.Filled.HelpOutline, SettingType.NAVIGATION),
        SettingItem("Sair", Icons.AutoMirrored.Filled.ExitToApp, SettingType.NAVIGATION, onClick = { /* Lógica de Logout */ })
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileHeader(
            user = currentUser,
            onEditClick = { /* Navegar para tela de edição */ },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        ActivityHistory(
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Grupos de Configurações
        SettingsGroup(title = "CONTA", items = accountItems)
        SettingsGroup(title = "PREFERÊNCIAS", items = preferencesItems)
        SettingsGroup(title = "SUPORTE", items = supportItems)
    }
}