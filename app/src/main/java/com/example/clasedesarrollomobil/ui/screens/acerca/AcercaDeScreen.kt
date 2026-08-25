package com.example.clasedesarrollomobil.ui.screens.acerca

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import com.example.clasedesarrollomobil.viewmodel.AccessLogViewModel

@Composable
fun AcercaDeScreen(
    accessLogViewModel: AccessLogViewModel,
    onBackToMenu: () -> Unit
) {
    val accessLogs by accessLogViewModel.recentAccessLogs.collectAsState()

    DemoScreenScaffold(
        title = "Acerca de",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Perfil académico",
            description = "Información de la aplicación y del estudiante."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reemplazar por una fotografía personal en drawable cuando esté disponible.
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Fotografía de perfil pendiente",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(84.dp)
                )
                Column {
                    Text("Emmanuel Martínez Díaz", style = MaterialTheme.typography.titleMedium)
                    Text("Licenciatura en Ingeniería de Software")
                    Text("Desarrollo Móvil")
                    Text("UAEMex")
                    Text("Versión 1.0")
                }
            }
        }

        DemoCard(
            title = "Descripción",
            description = "Aplicación desarrollada como integración de las prácticas realizadas durante el curso de Desarrollo Móvil."
        )

        DemoCard(
            title = "Últimos accesos",
            description = "Historial guardado con Room y observado con StateFlow."
        ) {
            if (accessLogs.isEmpty()) {
                Text("Aún no hay accesos registrados.")
            } else {
                LazyColumn(
                    modifier = Modifier.height(220.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(accessLogs) { access ->
                        Card {
                            Text(
                                text = "${access.user} - ${access.date}",
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
