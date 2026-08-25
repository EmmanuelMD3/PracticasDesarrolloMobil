package com.example.clasedesarrollomobil.ui.screens.listas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold

@Composable
fun ListasColeccionesScreen(onBackToMenu: () -> Unit) {
    val languages = listOf("Kotlin", "Java", "Python", "JavaScript", "Dart", "Swift")
    val technologies = listOf("Android", "Kotlin", "Compose", "Room", "Material", "Navigation")

    DemoScreenScaffold(
        title = "Listas y Colecciones",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "LazyColumn",
            description = "Lista vertical eficiente para colecciones grandes."
        ) {
            LazyColumn(
                modifier = Modifier.height(260.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(languages) { language ->
                    Card {
                        Text(
                            text = language,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        DemoCard(
            title = "LazyRow",
            description = "Carrusel horizontal desplazable para categorías o elementos compactos."
        ) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(technologies) { technology ->
                    Card {
                        Text(
                            text = technology,
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)
                        )
                    }
                }
            }
        }
    }
}
