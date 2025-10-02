package com.example.challenge2025.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userPrefsDataStore by preferencesDataStore("user_prefs")

object UserKeys {
    val PROFILE_PIC_URI = stringPreferencesKey("profile_pic_uri")
}

class UserPreferences(private val context: Context) {
    val profilePictureUri: Flow<String?> = context.userPrefsDataStore.data.map { prefs ->
        prefs[UserKeys.PROFILE_PIC_URI]
    }

    suspend fun saveProfilePictureUri(uri: String) {
        context.userPrefsDataStore.edit { prefs ->
            prefs[UserKeys.PROFILE_PIC_URI] = uri
        }
    }
}