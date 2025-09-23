package com.example.challenge2025.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    // 1. Pegamos o contexto atual do ambiente Composable.
    val context = LocalContext.current

    // 2. Usamos o 'remember' para fazer a verificação apenas uma vez.
    // O 'getIdentifier' procura um recurso pelo nome e tipo.
    // Ele retorna o ID do recurso se encontrar, ou 0 se não encontrar.
    val resourceId = remember {
        context.resources.getIdentifier("logo_sori", "drawable", context.packageName)
    }

    // 3. Verificamos se o recurso foi encontrado (ID diferente de 0).
    if (resourceId != 0) {
        // Se encontramos o logo_sori, usamos ele.
        Icon(
            painter = painterResource(id = resourceId),
            contentDescription = "App Logo",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = modifier
        )
    } else {
        // Se não, usamos o ícone genérico de fallback.
        Icon(
            imageVector = Icons.Rounded.Spa,
            contentDescription = "App Logo",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = modifier
        )
    }
}