package com.example.clasedesarrollomobil.ui.screens.acerca

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AcercaDeDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Perfil"
            )
        },
        title = { Text("Acerca de") },
        text = {
            Text(
                text = """
                    Emmanuel Martínez Díaz
                    Licenciatura en Ingeniería de Software
                    Desarrollo Móvil
                    UAEMex
                    Versión 1.0

                    Aplicación desarrollada para la práctica PASCM T2.4 Un solito.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Aceptar")
            }
        }
    )
}
