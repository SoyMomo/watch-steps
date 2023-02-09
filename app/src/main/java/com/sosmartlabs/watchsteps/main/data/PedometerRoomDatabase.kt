package com.sosmartlabs.watchsteps.main.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sosmartlabs.watchsteps.main.data.model.PedometerData

@Database(entities = [PedometerData::class], version = 1, exportSchema = false)
abstract class PedometerRoomDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: PedometerRoomDatabase? = null

        fun getInstance(context: Context): PedometerRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PedometerRoomDatabase::class.java,
                    "pedometer_data_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun pedometerDao(): PedometerDao
}