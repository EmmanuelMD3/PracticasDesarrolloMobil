package com.example.clasedesarrollomobil.ui.screens.dialogos

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import kotlinx.coroutines.launch

@Composable
fun DialogosMensajesScreen(onBackToMenu: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    DemoScreenScaffold(
        title = "Diálogos y Mensajes",
        onBackToMenu = onBackToMenu,
        snackbarHostState = snackbarHostState
    ) {
        DemoCard(
            title = "AlertDialog",
            description = "Muestra un cuadro modal para confirmar una acción."
        ) {
            Button(onClick = { showDialog = true }) {
                Text("Mostrar AlertDialog")
            }
        }

        DemoCard(
            title = "Snackbar",
            description = "Muestra un mensaje temporal dentro de Scaffold."
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Operación realizada correctamente")
                    }
                }
            ) {
                Text("Mostrar Snackbar")
            }
        }

        DemoCard(
            title = "Toast",
            description = "Muestra un mensaje breve del sistema Android."
        ) {
            Button(
                onClick = {
                    Toast.makeText(context, "Este es un mensaje Toast", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Mostrar Toast")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmación") },
            text = { Text("¿Deseas continuar?") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
