package com.sosmartlabs.watchsteps.main.data.model

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

data class Pedometer(
    var running: Boolean,
    var totalSteps: Float,
    var previousTotalSteps: Float,
    var sensorManager: SensorManager? = null,
) : SensorEventListener {
    fun resetSteps() {
        previousTotalSteps = totalSteps
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (running) {
            totalSteps = event.values[0]
            val currentSteps : Int = (totalSteps - previousTotalSteps).toInt()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Optional method to handle sensor accuracy changes
    }
}