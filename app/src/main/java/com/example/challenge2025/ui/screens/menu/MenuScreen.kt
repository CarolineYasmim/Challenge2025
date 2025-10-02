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
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challenge2025.ui.components.menu.ActivityHistory
import com.example.challenge2025.ui.components.menu.ProfileHeader
import com.example.challenge2025.ui.components.menu.SettingItem
import com.example.challenge2025.ui.components.menu.SettingType
import com.example.challenge2025.ui.components.menu.SettingsGroup
import com.example.challenge2025.ui.viewmodel.auth.AuthViewModel
import com.example.challenge2025.ui.viewmodel.user.UserViewModel

@Composable
fun MenuScreen(
    // MUDANÇA 1: Receber lambdas para as ações de navegação
    onNavigateToPersonalData: () -> Unit,
    onNavigateToCompanyData: () -> Unit,
    onNavigateToLanguage: () -> Unit,
    onNavigateToHelpCenter: () -> Unit,
    onLogout: () -> Unit
) {
    // MUDANÇA 2: Obter os ViewModels diretamente aqui com Hilt
    val userViewModel: UserViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUser by userViewModel.currentUser.collectAsState()

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                userViewModel.updateProfilePicture(uri.toString())
            }
        }
    )

    // MUDANÇA 3: Ações de clique agora chamam os lambdas recebidos
    val accountItems = listOf(
        SettingItem("Dados Pessoais", Icons.Rounded.Person, SettingType.NAVIGATION, onClick = onNavigateToPersonalData),
        SettingItem("Dados Corporativos", Icons.Rounded.Business, SettingType.NAVIGATION, onClick = onNavigateToCompanyData)
    )
    val preferencesItems = listOf(
        SettingItem("Notificações", Icons.Rounded.Notifications, SettingType.TOGGLE),
        SettingItem("Idioma", Icons.Rounded.Language, SettingType.NAVIGATION, onClick = onNavigateToLanguage)
    )
    val supportItems = listOf(
        SettingItem("Central de Ajuda", Icons.AutoMirrored.Filled.HelpOutline, SettingType.NAVIGATION, onClick = onNavigateToHelpCenter),
        SettingItem("Sair", Icons.AutoMirrored.Filled.ExitToApp, SettingType.NAVIGATION, onClick = {
            authViewModel.logout()
            onLogout()
        })
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
            onEditClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ActivityHistory(modifier = Modifier.padding(horizontal = 16.dp))
        SettingsGroup(title = "CONTA", items = accountItems)
        SettingsGroup(title = "PREFERÊNCIAS", items = preferencesItems)
        SettingsGroup(title = "SUPORTE", items = supportItems)
    }
}