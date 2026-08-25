package com.example.clasedesarrollomobil.ui.screens.navegacion

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold

@Composable
fun NavegacionDetalleScreen(
    nombre: String,
    onBackToMenu: () -> Unit,
    onBack: () -> Unit
) {
    DemoScreenScaffold(
        title = "Detalle de navegación",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Segunda pantalla",
            description = "Esta pantalla recibe un argumento desde la ruta de navegación."
        ) {
            Text("Dato recibido: $nombre")
            Button(onClick = onBack) {
                Text("Regresar")
            }
        }
    }
}
