package com.example.clasedesarrollomobil.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CamaraUiState(
    val photoUri: Uri? = null,
    val pendingPhotoUri: Uri? = null,
    val statusMessage: String = "Presiona el botón para tomar una fotografía."
)

class CamaraViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CamaraUiState())
    val uiState: StateFlow<CamaraUiState> = _uiState.asStateFlow()

    fun setPendingPhoto(uri: Uri) {
        _uiState.update {
            it.copy(
                pendingPhotoUri = uri,
                statusMessage = "Cámara lista."
            )
        }
    }

    fun onPhotoResult(saved: Boolean) {
        _uiState.update {
            if (saved) {
                it.copy(
                    photoUri = it.pendingPhotoUri,
                    statusMessage = "Fotografía guardada correctamente en Imágenes/ClaseDesarrolloMobil."
                )
            } else {
                it.copy(statusMessage = "No se guardó ninguna fotografía.")
            }
        }
    }

    fun onPermissionDenied() {
        _uiState.update {
            it.copy(statusMessage = "Permiso de cámara denegado.")
        }
    }
}
