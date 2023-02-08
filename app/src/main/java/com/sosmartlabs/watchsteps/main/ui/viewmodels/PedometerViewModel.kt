package com.sosmartlabs.watchsteps.main.ui.viewmodels

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosmartlabs.watchsteps.main.data.PedometerRoomDatabase
import com.sosmartlabs.watchsteps.main.data.model.Pedometer
import com.sosmartlabs.watchsteps.main.data.model.PedometerData
import com.sosmartlabs.watchsteps.main.data.repositories.PedometerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PedometerViewModel @Inject constructor(
    private val pedometerRepository: PedometerRepository,
    private val application: Application
) : ViewModel() {

    private var pedometer: Pedometer = Pedometer(running = false, totalSteps = 0f, previousTotalSteps = 0f)

    private val pedometerDatabase = PedometerRoomDatabase.getInstance(application)

    private val pedometerDao = pedometerDatabase.pedometerDao()

    fun start() {
        pedometer.running = true
        val stepSensor = pedometer.sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            pedometer.running = false
        } else{
            pedometer.sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            pedometer.sensorManager?.registerListener(pedometer, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop() {
        pedometer.running = false
    }

    fun insertPedometerData(pedometerData: PedometerData) {
        viewModelScope.launch(Dispatchers.IO) {
            pedometerDao.insertPedometerData(pedometerData)
        }
    }

    fun getAllPedometerData(): LiveData<PedometerData> {
        return pedometerDao.getPedometerData(DateUtils.formatDateTime(application, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE))
    }
}