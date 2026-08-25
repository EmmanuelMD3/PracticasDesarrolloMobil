package com.example.clasedesarrollomobil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.clasedesarrollomobil.data.local.AccessLogEntity
import com.example.clasedesarrollomobil.data.repository.AccessLogRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AccessLogViewModel(
    accessLogRepository: AccessLogRepository
) : ViewModel() {
    val recentAccessLogs: StateFlow<List<AccessLogEntity>> =
        accessLogRepository.recentAccessLogs.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}

class AccessLogViewModelFactory(
    private val accessLogRepository: AccessLogRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccessLogViewModel::class.java)) {
            return AccessLogViewModel(accessLogRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
