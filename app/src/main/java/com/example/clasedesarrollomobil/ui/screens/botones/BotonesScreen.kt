package com.example.clasedesarrollomobil.ui.screens.botones

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import kotlinx.coroutines.launch

@Composable
fun BotonesScreen(onBackToMenu: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var message by remember { mutableStateOf("Presiona un botón") }
    var counter by remember { mutableIntStateOf(0) }
    var enabled by remember { mutableStateOf(true) }

    DemoScreenScaffold(
        title = "Botones",
        onBackToMenu = onBackToMenu,
        snackbarHostState = snackbarHostState
    ) {
        DemoCard(
            title = "Estado de la pantalla",
            description = "Los botones modifican valores guardados con remember y mutableStateOf."
        ) {
            Text("Mensaje: $message")
            Text("Contador: $counter")
        }

        DemoCard(
            title = "Button y OutlinedButton",
            description = "Button ejecuta una acción principal; OutlinedButton una acción secundaria."
        ) {
            Button(onClick = { message = "Button presionado" }) {
                Text("Button")
            }
            OutlinedButton(onClick = { counter++ }) {
                Text("Incrementar contador")
            }
        }

        DemoCard(
            title = "TextButton e IconButton",
            description = "TextButton es ligero; IconButton usa un ícono Material."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Snackbar desde TextButton")
                        }
                    }
                ) {
                    Text("Mostrar Snackbar")
                }
                IconButton(onClick = { message = "IconButton favorito" }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorito"
                    )
                }
            }
        }

        DemoCard(
            title = "FloatingActionButton y botón deshabilitado",
            description = "El FAB incrementa el contador; el switch lógico activa o desactiva otro botón."
        ) {
            ExtendedFloatingActionButton(
                onClick = { counter++ },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar"
                    )
                },
                text = { Text("Sumar") }
            )
            Button(
                onClick = { message = "Botón habilitado ejecutado" },
                enabled = enabled
            ) {
                Text("Botón habilitado/deshabilitado")
            }
            OutlinedButton(onClick = { enabled = !enabled }) {
                Text(if (enabled) "Deshabilitar botón" else "Habilitar botón")
            }
        }
    }
}
