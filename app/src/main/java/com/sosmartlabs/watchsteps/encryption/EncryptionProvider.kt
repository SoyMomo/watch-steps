package com.sosmartlabs.watchfriends.encryption

import android.util.Base64
import timber.log.Timber
import java.io.IOException
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionProvider {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS7Padding"
    private const val IV = "3248928457812360"

    fun encrypt(deviceId: String, stringToEncrypt: String): String {
        var outputBytes = ByteArray(0)
        try {
            val key = "943892$deviceId"
            val secretKey = SecretKeySpec(key.toByteArray(), ALGORITHM)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val ivParameterSpec = IvParameterSpec(IV.toByteArray())
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
            outputBytes = cipher.doFinal(stringToEncrypt.toByteArray())
        } catch (ex: IOException) {
            Timber.e(ex.toString())
        }
        return Base64.encodeToString(outputBytes, 0).trim()
    }

    @Throws(Exception::class)
    fun decrypt(deviceId: String, stringToDecrypt: String?): String {
        val key = "943892$deviceId"
        val secretKey = SecretKeySpec(key.toByteArray(), ALGORITHM)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val ivParameterSpec = IvParameterSpec(IV.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        return String(cipher.doFinal(Base64.decode(stringToDecrypt, Base64.DEFAULT)))
    }

}