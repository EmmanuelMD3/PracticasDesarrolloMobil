package com.example.clasedesarrollomobil.ui.components

import androidx.compose.runtime.Composable

@Composable
internal fun DemoDateSection(currentDateTime: String) {
    DemoSection(
        title = "Fecha y hora de la demostración",
        description = currentDateTime
    )
}
