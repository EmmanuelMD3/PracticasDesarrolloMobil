package com.example.clasedesarrollomobil.ui.screens.seleccion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold

@Composable
fun SeleccionScreen(onBackToMenu: () -> Unit) {
    var accepted by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("Kotlin") }
    var sliderValue by remember { mutableFloatStateOf(50f) }
    var selectedChip by remember { mutableStateOf("Android") }
    val languages = listOf("Kotlin", "Java", "Dart")

    DemoScreenScaffold(
        title = "Selección",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Checkbox",
            description = "Permite marcar una opción independiente."
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = accepted,
                    onCheckedChange = { accepted = it }
                )
                Text("Acepto los términos: ${if (accepted) "Sí" else "No"}")
            }
        }

        DemoCard(
            title = "RadioButton",
            description = "Permite seleccionar una sola opción dentro de un grupo."
        ) {
            languages.forEach { language ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedLanguage == language,
                        onClick = { selectedLanguage = language }
                    )
                    Text(language)
                }
            }
            Text(
                text = "Seleccionado: $selectedLanguage",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        DemoCard(
            title = "Switch y Slider",
            description = "Switch controla valores booleanos; Slider permite elegir un rango."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Notificaciones activadas: ${if (notifications) "Sí" else "No"}")
                Switch(
                    checked = notifications,
                    onCheckedChange = { notifications = it }
                )
            }
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 0f..100f
            )
            Text("Nivel seleccionado: ${sliderValue.toInt()}")
        }

        DemoCard(
            title = "FilterChip y AssistChip",
            description = "Los chips permiten seleccionar o ejecutar acciones compactas."
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Android", "Compose", "Room").forEach { chip ->
                    FilterChip(
                        selected = selectedChip == chip,
                        onClick = { selectedChip = chip },
                        label = { Text(chip) }
                    )
                }
            }
            AssistChip(
                onClick = { selectedChip = "Material" },
                label = { Text("Elegir Material") }
            )
            Text("Chip seleccionado: $selectedChip")
        }
    }
}
