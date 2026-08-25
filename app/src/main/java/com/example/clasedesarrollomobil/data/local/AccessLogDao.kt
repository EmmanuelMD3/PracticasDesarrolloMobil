package com.example.clasedesarrollomobil.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AccessLogDao {
    @Insert
    suspend fun insertAccessLog(accessLog: AccessLogEntity)

    @Query("SELECT * FROM access_logs ORDER BY id DESC LIMIT 10")
    fun getRecentAccessLogs(): Flow<List<AccessLogEntity>>
}
