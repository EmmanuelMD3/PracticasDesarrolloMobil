package com.example.clasedesarrollomobil.ui.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.clasedesarrollomobil.navigation.Routes
import com.example.clasedesarrollomobil.ui.components.MenuButton

@Composable
fun MenuScreen(
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    var showExitDialog by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF7FAFC),
                            Color(0xFFE9F7F3),
                            Color(0xFFFFF7E8)
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Menú Principal",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF163B45),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Prácticas de Desarrollo Móvil",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF54656A),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 6.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(menuOptions) { index, option ->
                MenuButton(
                    text = option.title,
                    color = menuColors[index % menuColors.size],
                    onClick = {
                        if (option.route == null) {
                            showExitDialog = true
                        } else {
                            onNavigate(option.route)
                        }
                    }
                )
            }
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Salir") },
            text = { Text("¿Deseas salir de la aplicación?") },
            confirmButton = {
                TextButton(onClick = onLogout) {
                    Text("Salir")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

private data class MenuOption(
    val title: String,
    val route: String?
)

private val menuOptions = listOf(
    MenuOption("Texto", Routes.TEXTO),
    MenuOption("Botones", Routes.BOTONES),
    MenuOption("Selección", Routes.SELECCION),
    MenuOption("Listas y Colecciones", Routes.LISTAS),
    MenuOption("Imágenes y Multimedia", Routes.MULTIMEDIA),
    MenuOption("Barras e Indicadores", Routes.BARRAS),
    MenuOption("Navegación", Routes.NAVEGACION),
    MenuOption("Layout", Routes.LAYOUT),
    MenuOption("Fecha y hora", Routes.FECHA_HORA),
    MenuOption("Contenedores Desplazables", Routes.SCROLL),
    MenuOption("Diálogos y Mensajes", Routes.DIALOGOS),
    MenuOption("Material Design", Routes.MATERIAL),
    MenuOption("Google", Routes.GOOGLE),
    MenuOption("Acerca de", Routes.ACERCA),
    MenuOption("Salir", null)
)

private val menuColors = listOf(
    Color(0xFF0F766E),
    Color(0xFF2563EB),
    Color(0xFF7C3AED),
    Color(0xFFB45309),
    Color(0xFFBE123C),
    Color(0xFF047857)
)
