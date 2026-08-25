package com.example.clasedesarrollomobil.ui.screens.scroll

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold

@Composable
fun ContenedoresDesplazablesScreen(onBackToMenu: () -> Unit) {
    val items = (1..30).map { "Elemento vertical $it" }
    val horizontalItems = (1..15).map { "Card $it" }

    DemoScreenScaffold(
        title = "Contenedores Desplazables",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "LazyColumn",
            description = "Equivalente moderno a un ScrollView vertical eficiente."
        ) {
            LazyColumn(
                modifier = Modifier.height(240.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    Card {
                        Text(item, modifier = Modifier.padding(14.dp))
                    }
                }
            }
        }

        DemoCard(
            title = "LazyRow",
            description = "Lista horizontal eficiente, similar a un carrusel."
        ) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(horizontalItems) { item ->
                    Card {
                        Text(item, modifier = Modifier.padding(18.dp))
                    }
                }
            }
        }

        DemoCard(
            title = "horizontalScroll",
            description = "Permite desplazar manualmente una fila común."
        ) {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                horizontalItems.forEach { item ->
                    Card {
                        Text(item, modifier = Modifier.padding(18.dp))
                    }
                }
            }
        }
    }
}
