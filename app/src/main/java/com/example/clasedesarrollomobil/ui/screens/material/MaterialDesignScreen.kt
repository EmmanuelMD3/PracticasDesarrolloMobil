package com.example.clasedesarrollomobil.ui.screens.material

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@Composable
fun MaterialDesignScreen(onBackToMenu: () -> Unit) {
    DemoScreenScaffold(
        title = "Material Design",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "CardView",
            description = "Tarjeta para mostrar información."
        ) {
            AndroidView(
                factory = { context ->
                    CardView(context).apply {
                        radius = 18f
                        cardElevation = 8f
                        setContentPadding(28, 24, 28, 24)
                        addView(
                            textView(
                                context = context,
                                text = "Información dentro de un CardView clásico.",
                                textSize = 16f
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }

        DemoCard(
            title = "MaterialCardView",
            description = "Tarjeta con estilo Material."
        ) {
            AndroidView(
                factory = { context ->
                    val themedContext = materialContext(context)
                    MaterialCardView(themedContext).apply {
                        radius = 22f
                        strokeWidth = 3
                        strokeColor = 0xFF0F766E.toInt()
                        cardElevation = 10f
                        setCardBackgroundColor(0xFFE9F7F3.toInt())
                        setContentPadding(28, 24, 28, 24)
                        addView(
                            textView(
                                context = themedContext,
                                text = "MaterialCardView con borde, elevación y color.",
                                textSize = 16f
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )
        }

        DemoCard(
            title = "TextInputLayout",
            description = "Campo de texto con validaciones y etiqueta flotante."
        ) {
            AndroidView(
                factory = { context ->
                    val themedContext = materialContext(context)
                    TextInputLayout(themedContext).apply {
                        hint = "Nombre"
                        helperText = "Escribe al menos 3 caracteres."
                        addView(
                            TextInputEditText(themedContext).apply {
                                setSingleLine(true)
                                setText("")
                            }
                        )
                    }
                },
                update = { layout ->
                    val editText = layout.editText
                    editText?.setOnFocusChangeListener { _, _ ->
                        validateTextInput(layout)
                    }
                    editText?.setOnEditorActionListener { _, _, _ ->
                        validateTextInput(layout)
                        false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        DemoCard(
            title = "TextInputEditText",
            description = "Entrada de texto Material."
        ) {
            AndroidView(
                factory = { context ->
                    val themedContext = materialContext(context)
                    TextInputEditText(themedContext).apply {
                        hint = "Escribe un mensaje"
                        setSingleLine(true)
                        setText("Texto Material")
                        setPadding(24, 12, 24, 12)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        DemoCard(
            title = "ExtendedFloatingActionButton",
            description = "FAB con texto e ícono."
        ) {
            AndroidView(
                factory = { context ->
                    val themedContext = materialContext(context)
                    ExtendedFloatingActionButton(themedContext).apply {
                        text = "Guardar"
                        setIconResource(android.R.drawable.ic_menu_save)
                        setOnClickListener {
                            Toast.makeText(themedContext, "FAB presionado", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            )
        }

        DemoCard(
            title = "ChipGroup",
            description = "Agrupa chips."
        ) {
            AndroidView(
                factory = { context ->
                    val themedContext = materialContext(context)
                    ChipGroup(themedContext).apply {
                        isSingleSelection = true
                        isSelectionRequired = false
                        addChip(themedContext, "CardView")
                        addChip(themedContext, "TextInput")
                        addChip(themedContext, "FAB")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun materialContext(context: Context): Context {
    return ContextThemeWrapper(
        context,
        com.google.android.material.R.style.Theme_MaterialComponents_DayNight
    )
}

private fun textView(context: Context, text: String, textSize: Float): TextView {
    return TextView(context).apply {
        this.text = text
        this.textSize = textSize
        gravity = Gravity.CENTER_VERTICAL
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}

private fun ChipGroup.addChip(context: Context, text: String) {
    addView(
        Chip(context).apply {
            this.text = text
            isCheckable = true
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    )
}

private fun validateTextInput(layout: TextInputLayout) {
    val value = layout.editText?.text?.toString().orEmpty()
    layout.error = if (value.length in 1..2) {
        "Mínimo 3 caracteres"
    } else {
        null
    }
}
