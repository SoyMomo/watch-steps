package com.sosmartlabs.watchsteps.main.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedometer_table")
data class PedometerData(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "steps")
    val steps: Float
)
