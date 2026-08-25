package com.example.clasedesarrollomobil.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "access_logs")
data class AccessLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val user: String,
    val date: String
)
