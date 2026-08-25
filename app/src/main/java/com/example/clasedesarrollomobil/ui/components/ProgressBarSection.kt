package com.example.clasedesarrollomobil.ui.components

import android.widget.ProgressBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
internal fun ProgressBarSection(
    progress: Int,
    isProgressRunning: Boolean,
    onStartProgress: () -> Unit
) {
    DemoSection(
        title = "1. ProgressBar",
        description = "Representa visualmente el avance de una tarea."
    ) {
        // Configuración del ProgressBar clásico en modo horizontal.
        AndroidView(
            factory = { context ->
                ProgressBar(
                    context,
                    null,
                    android.R.attr.progressBarStyleHorizontal
                ).apply {
                    max = 100
                    isIndeterminate = false
                    setProgress(progress, false)
                }
            },
            update = { view ->
                view.max = 100
                view.setProgress(progress, true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
        )

        Text(
            text = "Progreso: $progress%",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onStartProgress,
            enabled = !isProgressRunning
        ) {
            Text("Iniciar progreso")
        }
    }
}
