package com.example.clasedesarrollomobil.ui.screens.multimedia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.clasedesarrollomobil.R
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold

@Composable
fun ImagenesMultimediaScreen(onBackToMenu: () -> Unit) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var webUrlInput by remember { mutableStateOf("https://www.uaemex.mx") }
    var loadedWebUrl by remember { mutableStateOf("https://www.uaemex.mx") }
    var surfaceTextInput by remember { mutableStateOf("Hola SurfaceView") }
    var renderedSurfaceText by remember { mutableStateOf("Hola SurfaceView") }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> selectedImageUri = uri }
    )
    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> selectedVideoUri = uri }
    )

    DemoScreenScaffold(
        title = "Imágenes y Multimedia",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "ImageView",
            description = "Muestra imágenes. La imagen local se carga desde drawable y también puedes seleccionar una imagen del celular."
        ) {
            // ImageView clásico dentro de Compose para demostrar el widget Android.
            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.drawable.kobe_lakers_local)
                    }
                },
                update = { imageView ->
                    val uri = selectedImageUri
                    if (uri == null) {
                        imageView.setImageResource(R.drawable.kobe_lakers_local)
                    } else {
                        imageView.setImageURI(uri)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            )
            Button(onClick = { imagePicker.launch("image/*") }) {
                Text("Cargar imagen desde mi celular")
            }
            Text("Imagen actual: ${if (selectedImageUri == null) "local desde código" else "seleccionada del celular"}")
        }

        DemoCard(
            title = "VideoView",
            description = "Reproduce videos seleccionados desde el celular."
        ) {
            val context = LocalContext.current
            AndroidView(
                factory = {
                    VideoView(context).apply {
                        setMediaController(MediaController(context).also { controller ->
                            controller.setAnchorView(this)
                        })
                    }
                },
                update = { videoView ->
                    selectedVideoUri?.let { uri ->
                        videoView.setVideoURI(uri)
                        videoView.seekTo(1)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
            Button(onClick = { videoPicker.launch("video/*") }) {
                Text("Cargar video desde mi celular")
            }
            Text("Después de elegir un video, usa los controles para reproducirlo.")
        }

        DemoCard(
            title = "WebView",
            description = "Muestra páginas web. Escribe una liga y WebView la carga con loadUrl()."
        ) {
            OutlinedTextField(
                value = webUrlInput,
                onValueChange = { webUrlInput = it },
                label = { Text("Liga o URL") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    loadedWebUrl = normalizeUrl(webUrlInput)
                }
            ) {
                Text("Cargar liga")
            }
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                        loadUrl(loadedWebUrl)
                    }
                },
                update = { webView ->
                    if (webView.url != loadedWebUrl) {
                        webView.loadUrl(loadedWebUrl)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            )
            Text("Liga cargada: $loadedWebUrl")
        }

        DemoCard(
            title = "SurfaceView",
            description = "Renderiza gráficos o texto sobre una superficie independiente. Escribe algo y mándalo al SurfaceView."
        ) {
            OutlinedTextField(
                value = surfaceTextInput,
                onValueChange = { surfaceTextInput = it },
                label = { Text("Texto para renderizar") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { renderedSurfaceText = surfaceTextInput.ifBlank { "SurfaceView" } }) {
                Text("Mandar al SurfaceView")
            }
            AndroidView(
                factory = { context ->
                    RenderingSurfaceView(context)
                },
                update = { surfaceView ->
                    surfaceView.renderMessage(renderedSurfaceText)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }
    }
}

private class RenderingSurfaceView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var message: String = "SurfaceView"

    init {
        holder.addCallback(this)
    }

    fun renderMessage(newMessage: String) {
        message = newMessage.ifBlank { "SurfaceView" }
        post { drawSurfaceDemo(holder, message) }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        drawSurfaceDemo(holder, message)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        drawSurfaceDemo(holder, message)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) = Unit
}

private fun normalizeUrl(url: String): String {
    val trimmedUrl = url.trim()
    return if (trimmedUrl.startsWith("http://") || trimmedUrl.startsWith("https://")) {
        trimmedUrl
    } else {
        "https://$trimmedUrl"
    }
}

private fun drawSurfaceDemo(holder: SurfaceHolder, message: String) {
    if (!holder.surface.isValid) return

    val canvas: Canvas = holder.lockCanvas() ?: return
    try {
        canvas.drawColor(Color.rgb(15, 118, 110))
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = 44f
        }
        canvas.drawCircle(canvas.width * 0.78f, canvas.height * 0.45f, 48f, paint)
        canvas.drawText(message.take(18), 36f, canvas.height * 0.45f, paint)
        paint.textSize = 28f
        canvas.drawText("Renderizado de gráficos", 36f, canvas.height * 0.65f, paint)
    } finally {
        holder.unlockCanvasAndPost(canvas)
    }
}
