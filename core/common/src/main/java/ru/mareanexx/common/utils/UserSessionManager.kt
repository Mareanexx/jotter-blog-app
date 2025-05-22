package ru.mareanexx.common.utils

import java.util.UUID
import javax.inject.Inject

class UserSessionManager @Inject constructor(private val dataStore: DataStore) {

    suspend fun saveSession(token: String, userUuid: UUID) {
        dataStore.saveToken(token)
        dataStore.saveUserUuid(userUuid)
    }

    suspend fun saveProfileId(profileId: Int) = dataStore.saveProfileId(profileId)

    suspend fun clearSession() = dataStore.clearAll()

    suspend fun getUserUuid(): UUID = dataStore.getUserUuid().let { UUID.fromString(it) }

    suspend fun getProfileId() = dataStore.getProfileId()

    suspend fun getToken() = dataStore.getToken()
}