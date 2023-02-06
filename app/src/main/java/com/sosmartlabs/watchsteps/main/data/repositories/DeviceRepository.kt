package com.sosmartlabs.watchsteps.main.data.repositories

import android.content.Context
import android.telephony.TelephonyManager
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
    /**
     * Debug imei set on application when running on emulator
     */
    var debugImei: String? = null

    private fun getIMEI(): String? {
        if (debugImei != null) return debugImei
        runCatching {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.deviceId
        }.onSuccess { imei ->
            if (imei.isNotEmpty()) {
                return imei
            }
        }.onFailure {
            Timber.e(it)
        }
        return null
    }

    fun getDeviceId(): String {
        return try {
            val imei = getIMEI()
            if (!imei.isNullOrEmpty()) {
                val deviceId = imei.substring(4, 14)
                Timber.d("getDeviceId: $deviceId")
                deviceId
            }
            else {
                Timber.d("getDeviceId: IMEI is null or empty, getting debug device id")
                "0903683249"
            }
        } catch (e: Exception) {
            Timber.e(e)
            Timber.d("getDeviceId: IMEI is null or empty, getting debug device id")
            "0903683249"
        }
    }

}