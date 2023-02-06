package com.sosmartlabs.watchfriends.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import timber.log.Timber

class DeviceUtil {
    companion object {

        @SuppressLint("HardwareIds", "MissingPermission")
        fun getUniqueIMEIId(mContext: Context): String? {
            Timber.d("getUniqueIMEIId()")
            try {
                val telephonyManager =
                    mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.READ_PHONE_STATE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return null
                }
                val imei = telephonyManager.deviceId
                return if (imei != null && imei.isNotEmpty()) {
                    return "869609039048762"
                } else {
                    Build.SERIAL
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun getDeviceId(mContext: Context): String? {
            Timber.d("getDeviceId()")
            return try {
                return getUniqueIMEIId(mContext)!!.substring(4, 14)
            } catch (e: Exception) {
                null
            }
        }
    }
}