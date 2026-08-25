package com.example.clasedesarrollomobil.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun LinearProgressIndicatorSection(progress: Int) {
    DemoSection(
        title = "4. LinearProgressIndicator",
        description = "Indicador de progreso lineal basado en Material Design."
    ) {
        // Actualización del indicador lineal usando el mismo estado del ProgressBar.
        LinearProgressIndicator(
            progress = { progress / 100f },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Progreso Material: $progress%",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
