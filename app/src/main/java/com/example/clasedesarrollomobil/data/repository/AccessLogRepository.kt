package com.example.clasedesarrollomobil.data.repository

import com.example.clasedesarrollomobil.data.local.AccessLogDao
import com.example.clasedesarrollomobil.data.local.AccessLogEntity
import kotlinx.coroutines.flow.Flow

class AccessLogRepository(
    private val accessLogDao: AccessLogDao
) {
    val recentAccessLogs: Flow<List<AccessLogEntity>> = accessLogDao.getRecentAccessLogs()

    suspend fun saveAccess(user: String, date: String) {
        accessLogDao.insertAccessLog(
            AccessLogEntity(
                user = user,
                date = date
            )
        )
    }
}
