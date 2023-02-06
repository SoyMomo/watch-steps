package com.sosmartlabs.watchsteps.main.data.repositories

import com.parse.coroutines.callCloudFunction
import com.sosmartlabs.watchsteps.main.data.model.Avatar
import com.sosmartlabs.watchsteps.main.data.model.Wearer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class WearerRepository @Inject constructor(
    private val deviceRepository: DeviceRepository,
) {
    private lateinit var wearer: Wearer

    @Inject
    lateinit var ioContext: CoroutineContext

    @Inject
    lateinit var externalScope: CoroutineScope

    fun getWearer(): Wearer {
        if (::wearer.isInitialized) {
            return wearer
        }
        throw IllegalStateException("Wearer not initialized")
    }

    fun getAvatar(): Avatar? {
        if (::wearer.isInitialized) {
            val avatar = wearer.avatar
            Timber.d("avatar: $avatar")
            return avatar?.fetchIfNeeded()
        }
        throw IllegalStateException("Wearer not initialized")
    }

    fun initializeWearer() {
        Timber.d("initializeWearer")
        externalScope.launch(ioContext) {
            Timber.d("Initializing Wearer")
            try {
                wearer = fetchWearerAsync().fetchIfNeeded()
                Timber.d("Wearer initialized: ${wearer.deviceId}")
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private suspend fun fetchWearerAsync(): Wearer {
        try {
            val deviceId = deviceRepository.getDeviceId()
            val wearer = callCloudFunction<Wearer>("fetchWearer", mapOf("deviceId" to deviceId))
            Timber.d("wearer: $wearer")
            return wearer
        } catch (e: Exception) {
            Timber.e(e)
            throw e
        }
    }

}