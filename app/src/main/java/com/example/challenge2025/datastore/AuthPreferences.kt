package com.example.challenge2025.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Criando a instância do DataStore como extensão do Context
private val Context.dataStore by preferencesDataStore("auth_prefs")

// Definição das chaves que vamos salvar no DataStore
object AuthKeys {
    val TOKEN = stringPreferencesKey("auth_token")
    val EMAIL = stringPreferencesKey("auth_email") // opcional, se quiser saber quem logou
}

class AuthPreferences(private val context: Context) {

    // Expondo o token como Flow (observável)
    val authToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[AuthKeys.TOKEN]
    }

    // Salvando token e email
    suspend fun saveAuth(token: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[AuthKeys.TOKEN] = token
            prefs[AuthKeys.EMAIL] = email
        }
    }

    // Limpando dados de autenticação
    suspend fun clearAuth() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
