package com.example.challenge2025.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Modelos para os itens de configuração
enum class SettingType { NAVIGATION, TOGGLE }
data class SettingItem(
    val title: String,
    val icon: ImageVector,
    val type: SettingType,
    val onClick: () -> Unit = {},
    val onToggle: (Boolean) -> Unit = {}
)

@Composable
fun SettingsGroup(
    title: String,
    items: List<SettingItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        items.forEach { item ->
            SettingsRow(item = item)
        }
    }
}

@Composable
private fun SettingsRow(item: SettingItem) {
    var isToggled by remember { mutableStateOf(true) }

    val rowModifier = if (item.type == SettingType.NAVIGATION) {
        Modifier.clickable(onClick = item.onClick)
    } else {
        Modifier
    }

    Row(
        modifier = rowModifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = if(item.icon == Icons.AutoMirrored.Filled.ExitToApp) MaterialTheme.colorScheme.error else LocalContentColor.current
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.title,
            modifier = Modifier.weight(1f),
            color = if(item.icon == Icons.AutoMirrored.Filled.ExitToApp) MaterialTheme.colorScheme.error else LocalContentColor.current
        )

        when(item.type) {
            SettingType.NAVIGATION -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Navegar",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            SettingType.TOGGLE -> {
                Switch(
                    checked = isToggled,
                    onCheckedChange = {
                        isToggled = it
                        item.onToggle(it)
                    }
                )
            }
        }
    }
}