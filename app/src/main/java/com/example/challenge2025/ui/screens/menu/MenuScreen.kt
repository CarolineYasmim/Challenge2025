package com.example.challenge2025.ui.screens.menu

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.challenge2025.ui.components.menu.ActivityHistory
import com.example.challenge2025.ui.components.menu.ProfileHeader
import com.example.challenge2025.ui.components.menu.SettingItem
import com.example.challenge2025.ui.components.menu.SettingType
import com.example.challenge2025.ui.components.menu.SettingsGroup
import com.example.challenge2025.viewmodel.AuthViewModel
import com.example.challenge2025.viewmodel.UserViewModel

@Composable
fun MenuScreen(
    userViewModel: UserViewModel = viewModel(),
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUser by userViewModel.currentUser.collectAsState()

    // Launcher de seleção de foto (somente nesta tela)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                userViewModel.updateProfilePicture(uri.toString())
            }
        }
    )

    // Listas de configurações
    val accountItems = listOf(
        SettingItem("Dados Pessoais", Icons.Rounded.Person, SettingType.NAVIGATION),
        SettingItem("Dados Corporativos", Icons.Rounded.Business, SettingType.NAVIGATION)
    )
    val preferencesItems = listOf(
        SettingItem("Notificações", Icons.Rounded.Notifications, SettingType.TOGGLE),
        SettingItem("Idioma", Icons.Rounded.Language, SettingType.NAVIGATION)
    )
    val supportItems = listOf(
        SettingItem(
            "Central de Ajuda",
            Icons.AutoMirrored.Filled.HelpOutline,
            SettingType.NAVIGATION
        ),
        SettingItem(
            "Sair",
            Icons.AutoMirrored.Filled.ExitToApp,
            SettingType.NAVIGATION,
            onClick = {authViewModel.logout()})
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header do perfil com lógica de alterar foto
        ProfileHeader(
            user = currentUser,
            onEditClick = {
                // Ao clicar no ícone de editar, abre o seletor de imagem
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Histórico de atividades
        ActivityHistory(
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Grupos de Configurações
        SettingsGroup(title = "CONTA", items = accountItems)
        SettingsGroup(title = "PREFERÊNCIAS", items = preferencesItems)
        SettingsGroup(title = "SUPORTE", items = supportItems)
    }
}
