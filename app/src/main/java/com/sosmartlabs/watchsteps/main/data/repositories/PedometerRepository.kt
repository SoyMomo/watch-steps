package com.sosmartlabs.watchsteps.main.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.sosmartlabs.watchsteps.main.data.PedometerDao
import com.sosmartlabs.watchsteps.main.data.PedometerRoomDatabase
import com.sosmartlabs.watchsteps.main.data.model.PedometerData
import java.text.SimpleDateFormat
import java.util.*

class PedometerRepository(application: Application) {
    private val pedometerDao: PedometerDao
    private val pedometerData: LiveData<PedometerData>

    init {
        val database = PedometerRoomDatabase.getInstance(application)
        pedometerDao = database.pedometerDao()
        pedometerData = pedometerDao.getPedometerData(getCurrentDate())
    }

    fun getPedometerData(date: String): LiveData<PedometerData> {
        return pedometerDao.getPedometerData(date)
    }

    fun insertPedometerData(pedometerData: PedometerData) {
        pedometerDao.insertPedometerData(pedometerData)
    }

    fun deleteOldData(date: String) {
        pedometerDao.deleteOldData(date)
    }

    private fun getCurrentDate(): String {
        val current = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(current)
    }
}
