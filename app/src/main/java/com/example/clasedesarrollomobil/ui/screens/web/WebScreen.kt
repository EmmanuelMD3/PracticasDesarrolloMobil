package com.example.clasedesarrollomobil.ui.screens.web

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold

@Composable
fun WebScreen(onBackToMenu: () -> Unit) {
    var urlInput by remember { mutableStateOf("https://www.uaemex.mx") }
    var loadedUrl by remember { mutableStateOf("https://www.uaemex.mx") }
    var message by remember { mutableStateOf("Escribe una URL y presiona Ir.") }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var canGoBack by remember { mutableStateOf(false) }

    BackHandler(enabled = canGoBack) {
        webViewRef?.goBack()
        canGoBack = webViewRef?.canGoBack() == true
    }

    DemoScreenScaffold(
        title = "Web",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Navegador web embebido",
            description = "WebView carga páginas dentro de la app sin abrir navegador externo."
        ) {
            OutlinedTextField(
                value = urlInput,
                onValueChange = { urlInput = it },
                label = { Text("URL") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val normalized = normalizeWebUrl(urlInput)
                    if (isUrlValid(normalized)) {
                        loadedUrl = normalized
                        message = "Cargando: $normalized"
                    } else {
                        message = "URL no válida"
                    }
                }
            ) {
                Text("Ir")
            }
            Button(
                onClick = {
                    webViewRef?.goBack()
                    canGoBack = webViewRef?.canGoBack() == true
                },
                enabled = canGoBack
            ) {
                Text("Atrás en WebView")
            }
            Text(message)

            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                canGoBack = this@apply.canGoBack()
                                message = "Página cargada: ${url.orEmpty()}"
                            }
                        }
                        settings.javaScriptEnabled = true
                        loadUrl(loadedUrl)
                        webViewRef = this
                    }
                },
                update = { webView ->
                    if (webView.url != loadedUrl) {
                        webView.loadUrl(loadedUrl)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp)
            )
        }
    }
}

private fun normalizeWebUrl(url: String): String {
    val trimmed = url.trim()
    return if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
        trimmed
    } else {
        "https://$trimmed"
    }
}

private fun isUrlValid(url: String): Boolean {
    val parsed = Uri.parse(url)
    return parsed.scheme in listOf("http", "https") && !parsed.host.isNullOrBlank()
}
