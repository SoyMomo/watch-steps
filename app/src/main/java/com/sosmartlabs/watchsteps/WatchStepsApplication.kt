package com.sosmartlabs.watchsteps

import android.os.Build
import android.util.Base64
import androidx.databinding.ktx.BuildConfig
import com.facebook.appevents.internal.Constants
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application
import com.parse.Parse
import com.parse.ParseObject
import com.sosmartlabs.watchfriends.session.SessionRepository
import com.sosmartlabs.watchsteps.main.data.model.Avatar
import com.sosmartlabs.watchsteps.main.data.model.AvatarItem
import com.sosmartlabs.watchsteps.main.data.model.WatchWearer
import com.sosmartlabs.watchsteps.main.data.model.Wearer
import com.sosmartlabs.watchsteps.main.data.repositories.DeviceRepository
import com.sosmartlabs.watchsteps.main.data.repositories.WearerRepository
import com.sosmartlabs.watchsteps.util.Constants.Companion.EMULATOR_ARM
import com.sosmartlabs.watchsteps.util.Constants.Companion.EMULATOR_X86
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.text.ParseException
import javax.inject.Inject


@HiltAndroidApp
class WatchStepsApplication: android.app.Application(){

    @Inject
    lateinit var deviceRepository: DeviceRepository

    @Inject
    lateinit var sessionRepository: SessionRepository

    @Inject
    lateinit var wearerRepository: WearerRepository
    

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initParse()
        setDeveloperMode()
    }


    private fun initParse() {

        registerSubclasses()

        val parseAppKey = if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE == "develop") String(
            Base64.decode(
                "RWNzZTNvbWF5bDhZN1hmbnI2dll2ZkJJNEJiRDlJblloS2Mxa2dLYw==",
                Base64.DEFAULT
            )
        ) else String(
            Base64.decode(
                "bHlNSHV3Y3VscmF0dzZIeWhRWW15NGFkT240NGxndlYxaFAwR1lRUw==",
                Base64.DEFAULT
            )
        )
        val parseClientKey = if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE == "develop") String(
            Base64.decode(
                "OEEzSE96QXh4NVdUeng3UFpNZGxEQXdGRUlmcEM3eUJZR0tIVVZPcQ==",
                Base64.DEFAULT
            )
        ) else String(
            Base64.decode(
                "NGFYeEFpb1NFNDdWZ2EwTUVUVXY1UlhxamQwN3B5YndtRjhFeDB6VQ==",
                Base64.DEFAULT
            )
        )
        val parseServerUrl = if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE == "develop") String(
            Base64.decode(
                "aHR0cHM6Ly9tb21vZGV2LmJhY2s0YXBwLmlvLw==",
                Base64.DEFAULT
            )
        ) else String(
            Base64.decode("aHR0cHM6Ly9tb21vLmJhY2s0YXBwLmlv", Base64.DEFAULT)
        )


        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(parseAppKey)
                .clientKey(parseClientKey)
                .server(parseServerUrl)
                .build()
        )
        sessionRepository.login()
        wearerRepository.initializeWearer()
    }

    private fun registerSubclasses() {
        ParseObject.registerSubclass(WatchWearer::class.java)
        ParseObject.registerSubclass(Wearer::class.java)
        ParseObject.registerSubclass(Avatar::class.java)
        ParseObject.registerSubclass(AvatarItem::class.java)
    }

    private fun initTimber(){
        if(BuildConfig.DEBUG || BuildConfig.BUILD_TYPE == "pre_release" || BuildConfig.BUILD_TYPE == "develop"){
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setDeveloperMode() {
        if (isRunningOnSpace3Emulator()) {
            deviceRepository.debugImei = "869609039048762"
        }
    }

    private fun isRunningOnSpace3Emulator(): Boolean {
        if (Build.MODEL in listOf(EMULATOR_ARM, EMULATOR_X86) && Build.VERSION.SDK_INT == 27) {
            return true
        }
        return false
    }
}