package com.example.clasedesarrollomobil.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ImagenesUiState(
    val selectedImages: List<Uri> = emptyList(),
    val message: String = "Selecciona exactamente 10 imágenes."
)

class ImagenesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ImagenesUiState())
    val uiState: StateFlow<ImagenesUiState> = _uiState.asStateFlow()

    fun onImagesSelected(images: List<Uri>) {
        val acceptedImages = images.take(10)
        val message = when {
            acceptedImages.size == 10 -> "Selección válida"
            acceptedImages.size < 10 -> "Debes seleccionar exactamente 10 imágenes"
            else -> "No se aceptan más de 10 imágenes"
        }
        _uiState.update {
            it.copy(
                selectedImages = acceptedImages,
                message = message
            )
        }
    }

    fun clearSelection() {
        _uiState.value = ImagenesUiState()
    }
}
