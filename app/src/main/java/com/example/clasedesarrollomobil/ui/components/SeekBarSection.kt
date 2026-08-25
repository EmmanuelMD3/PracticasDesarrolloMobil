package com.example.clasedesarrollomobil.ui.components

import android.widget.SeekBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
internal fun SeekBarSection(
    seekValue: Int,
    onValueChanged: (Int) -> Unit
) {
    DemoSection(
        title = "2. SeekBar",
        description = "Permite seleccionar un valor deslizando una barra."
    ) {
        Text(
            text = "Valor seleccionado: $seekValue",
            style = MaterialTheme.typography.bodyLarge
        )

        AndroidView(
            factory = { context ->
                SeekBar(context).apply {
                    max = 100
                    progress = seekValue

                    // Listener del SeekBar para actualizar el valor en tiempo real.
                    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar?,
                            value: Int,
                            fromUser: Boolean
                        ) {
                            if (fromUser) onValueChanged(value)
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

                        override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
                    })
                }
            },
            update = { view ->
                if (view.progress != seekValue) view.progress = seekValue
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
