package com.sosmartlabs.watchsteps.main.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sosmartlabs.watchsteps.main.data.model.PedometerData

@Dao
interface PedometerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPedometerData(pedometerData: PedometerData)

    @Query("SELECT * FROM PedometerData WHERE date = :date")
    fun getPedometerData(date: String): LiveData<PedometerData>

    @Query("DELETE FROM PedometerData WHERE date < :date")
    fun deleteOldData(date: String)
}