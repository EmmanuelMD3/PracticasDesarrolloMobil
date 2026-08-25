package com.example.clasedesarrollomobil.ui.screens.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold

@Composable
fun LayoutScreen(onBackToMenu: () -> Unit) {
    DemoScreenScaffold(
        title = "Layout",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Column",
            description = "Organiza elementos de forma vertical."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Elemento 1")
                Text("Elemento 2")
                Text("Elemento 3")
            }
        }

        DemoCard(
            title = "Row",
            description = "Organiza elementos horizontalmente."
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("A")
                Text("B")
                Text("C")
            }
        }

        DemoCard(
            title = "Box",
            description = "Permite superponer elementos dentro del mismo contenedor."
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFF2563EB))
                )
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .offset(x = 24.dp, y = 18.dp)
                        .background(Color(0xFFF59E0B))
                )
                Text(
                    text = "Encima",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        DemoCard(
            title = "Spacer",
            description = "Crea separación entre componentes."
        ) {
            Text("Arriba")
            Spacer(modifier = Modifier.height(24.dp))
            Text("Abajo después del Spacer", modifier = Modifier.padding(top = 4.dp))
        }
    }
}
