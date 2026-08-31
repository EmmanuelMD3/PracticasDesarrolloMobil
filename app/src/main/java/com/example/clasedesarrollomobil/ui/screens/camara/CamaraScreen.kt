package com.example.clasedesarrollomobil.ui.screens.camara

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import com.example.clasedesarrollomobil.viewmodel.CamaraViewModel

@Composable
fun CamaraScreen(
    onBackToMenu: () -> Unit,
    viewModel: CamaraViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = viewModel::onPhotoResult
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                val uri = createImageUri(context)
                viewModel.setPendingPhoto(uri)
                takePictureLauncher.launch(uri)
            } else {
                viewModel.onPermissionDenied()
            }
        }
    )

    DemoScreenScaffold(
        title = "Tomar fotografía",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Cámara",
            description = "Usa Activity Result API, permiso de cámara y MediaStore para guardar la foto."
        ) {
            Button(
                onClick = {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        val uri = createImageUri(context)
                        viewModel.setPendingPhoto(uri)
                        takePictureLauncher.launch(uri)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            ) {
                Text("Tomar fotografía")
            }
            Text(uiState.statusMessage)

            AndroidView(
                factory = { imageContext ->
                    ImageView(imageContext).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { imageView ->
                    uiState.photoUri?.let { imageView.setImageURI(it) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val name = "foto_${System.currentTimeMillis()}.jpg"
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, name)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/ClaseDesarrolloMobil")
        }
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        ?: error("No se pudo crear el archivo de fotografía")
}
