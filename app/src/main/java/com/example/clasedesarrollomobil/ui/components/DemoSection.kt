package com.example.clasedesarrollomobil.ui.components

import androidx.compose.runtime.Composable

@Composable
internal fun DemoSection(
    title: String,
    description: String,
    content: (@Composable () -> Unit)? = null
) {
    DemoCard(
        title = title,
        description = description,
        content = content
    )
}
