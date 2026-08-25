package com.example.clasedesarrollomobil.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun CircularProgressIndicatorSection(
    showCircularIndicator: Boolean,
    onVisibilityChanged: (Boolean) -> Unit
) {
    DemoSection(
        title = "5. CircularProgressIndicator",
        description = "Indicador circular utilizado para representar una tarea en ejecución."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mostrar indicador",
                style = MaterialTheme.typography.bodyLarge
            )

            // Control del indicador circular indeterminado.
            Switch(
                checked = showCircularIndicator,
                onCheckedChange = onVisibilityChanged
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp),
            contentAlignment = Alignment.Center
        ) {
            if (showCircularIndicator) {
                CircularProgressIndicator(
                    modifier = Modifier.size(72.dp),
                    strokeWidth = 6.dp
                )
            }
        }
    }
}
