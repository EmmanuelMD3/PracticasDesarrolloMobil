package com.example.clasedesarrollomobil.ui.screens.texto

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold

@Composable
fun TextoScreen(onBackToMenu: () -> Unit) {
    var textFieldValue by rememberSaveable { mutableStateOf("Texto editable") }
    var outlinedValue by rememberSaveable { mutableStateOf("Compose") }

    DemoScreenScaffold(
        title = "Texto",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Text",
            description = "Permite mostrar información dentro de una interfaz creada con Compose."
        ) {
            Text("Texto normal")
            Text("Texto en negritas", fontWeight = FontWeight.Bold)
            Text("Texto grande", fontSize = 24.sp)
            Text("Texto con color", color = Color(0xFF0F766E))
            Text(
                text = "Texto alineado al centro",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        DemoCard(
            title = "Modifier en Text",
            description = "Modifier permite aplicar padding, ancho, tamaño y alineación."
        ) {
            Text(
                text = "Este texto usa fillMaxWidth, altura fija y alineación dentro del componente.",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        DemoCard(
            title = "TextField",
            description = "TextField permite capturar información escrita por el usuario."
        ) {
            TextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                label = { Text("TextField") },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Valor: $textFieldValue",
                modifier = Modifier.fillMaxWidth()
            )
        }

        DemoCard(
            title = "OutlinedTextField",
            description = "OutlinedTextField muestra un campo con borde Material Design."
        ) {
            OutlinedTextField(
                value = outlinedValue,
                onValueChange = { outlinedValue = it },
                label = { Text("OutlinedTextField") },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Valor: $outlinedValue",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
