package com.example.clasedesarrollomobil.ui.screens.navegacion

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold

@Composable
fun NavegacionDemoScreen(
    onBackToMenu: () -> Unit,
    onOpenDetail: (String) -> Unit
) {
    DemoScreenScaffold(
        title = "Navegación",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Navigation Compose",
            description = "Esta pantalla usa NavHost, composable, rememberNavController y navigate()."
        ) {
            Text("Dato que se enviará: Emmanuel")
            Button(onClick = { onOpenDetail("Emmanuel") }) {
                Text("Ir a pantalla de detalle")
            }
        }
    }
}
