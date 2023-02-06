package com.sosmartlabs.watchfriends.session

import com.parse.ParseUser
import com.sosmartlabs.watchfriends.encryption.EncryptionProvider
import com.sosmartlabs.watchsteps.main.data.repositories.DeviceRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(
    private val deviceRepository: DeviceRepository
) {


    fun login(): ParseUser? {
        return try {
            val currentUser = getCurrentUser()
            if (currentUser != null) {
                return currentUser
            }
            val deviceId = deviceRepository.getDeviceId()
            val password = EncryptionProvider.encrypt(deviceId, deviceId)
            ParseUser.logIn(deviceId, password)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    private fun getCurrentUser(): ParseUser? {
        return try {
            val currentUser = ParseUser.getCurrentUser()
            if (currentUser != null && currentUser.isAuthenticated) {
                Timber.d("currentUser authenticated")
                return currentUser
            }
            null
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}