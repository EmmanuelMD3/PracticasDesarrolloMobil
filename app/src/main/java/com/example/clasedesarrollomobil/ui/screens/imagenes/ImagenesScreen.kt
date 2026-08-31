package com.example.clasedesarrollomobil.ui.screens.imagenes

import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import com.example.clasedesarrollomobil.viewmodel.ImagenesViewModel

@Composable
fun ImagenesScreen(
    onBackToMenu: () -> Unit,
    viewModel: ImagenesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(10),
        onResult = viewModel::onImagesSelected
    )

    DemoScreenScaffold(
        title = "Imágenes",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Selección exacta de 10 imágenes",
            description = "Usa Photo Picker moderno y valida que sean exactamente 10 imágenes."
        ) {
            Button(
                onClick = {
                    picker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
                Text("Seleccionar imágenes")
            }
            OutlinedButton(onClick = viewModel::clearSelection) {
                Text("Limpiar selección")
            }
            Text("Seleccionadas: ${uiState.selectedImages.size} / 10")
            Text(uiState.message)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp)
            ) {
                items(uiState.selectedImages) { uri ->
                    AndroidView(
                        factory = { context ->
                            ImageView(context).apply {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                            }
                        },
                        update = { imageView ->
                            imageView.setImageURI(uri)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    )
                }
            }
        }
    }
}
