package com.sosmartlabs.watchsteps.main.data.repositories

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.sosmartlabs.watchsteps.main.data.PedometerDao
import com.sosmartlabs.watchsteps.main.data.PedometerRoomDatabase
import com.sosmartlabs.watchsteps.main.data.model.PedometerData
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PedometerRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val pedometerDao: PedometerDao
    private val pedometerData: LiveData<PedometerData>

    init {
        val database = PedometerRoomDatabase.getInstance(context)
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
