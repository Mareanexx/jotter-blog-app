package ru.mareanexx.common.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.util.UUID

val Context.dataStore by preferencesDataStore(name = "settings")

class DataStore(private val context: Context) {
    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USER_UUID = stringPreferencesKey("user_uuid")
        val PROFILE_ID = intPreferencesKey("profile_id")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.USER_TOKEN] = token
        }
    }

    suspend fun getToken(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PreferencesKeys.USER_TOKEN] ?: ""
    }

    suspend fun saveProfileId(value: Int) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.PROFILE_ID] = value
        }
    }

    suspend fun getProfileId(): Int {
        val prefs = context.dataStore.data.first()
        return prefs[PreferencesKeys.PROFILE_ID] ?: -1
    }

    suspend fun saveUserUuid(userUuid: UUID) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.USER_UUID] = userUuid.toString()
        }
    }

    suspend fun getUserUuid(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PreferencesKeys.USER_UUID] ?: ""
    }

    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.USER_UUID] = ""
            prefs[PreferencesKeys.USER_TOKEN] = ""
        }
    }
}