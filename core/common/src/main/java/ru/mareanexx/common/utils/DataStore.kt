package ru.mareanexx.common.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

val Context.dataStore by preferencesDataStore(name = "settings")

class DataStore(private val context: Context) {
    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USER_UUID = stringPreferencesKey("user_uuid")
        val PROFILE_ID = intPreferencesKey("profile_id")
        val THEME = booleanPreferencesKey("theme")
        val NOTIFICATIONS = booleanPreferencesKey("notifications")
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

    suspend fun setTheme(isLight: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.THEME] = isLight
        }
    }

    suspend fun setNotifications(isOn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.NOTIFICATIONS] = isOn
        }
    }

    suspend fun getTheme(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[PreferencesKeys.THEME] ?: true
    }

    fun trackTheme(): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[PreferencesKeys.THEME] ?: true
        }
    }

    suspend fun getNotifications(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[PreferencesKeys.NOTIFICATIONS] ?: true
    }
}