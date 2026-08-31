package com.example.clasedesarrollomobil.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GpsUiState(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val message: String = "Solicita ubicación para centrar el mapa."
)

class GpsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GpsUiState())
    val uiState: StateFlow<GpsUiState> = _uiState.asStateFlow()

    fun onLocationReceived(location: Location?) {
        _uiState.update {
            if (location == null) {
                it.copy(message = "No se pudo obtener ubicación. Revisa que el GPS esté activado.")
            } else {
                it.copy(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    message = "Ubicación actual obtenida."
                )
            }
        }
    }

    fun onPermissionDenied() {
        _uiState.update {
            it.copy(message = "Permiso de ubicación denegado.")
        }
    }
}
