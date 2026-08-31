package com.example.clasedesarrollomobil.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.clasedesarrollomobil.ui.components.AppTopBar
import com.example.clasedesarrollomobil.ui.tabs.CargaTab
import com.example.clasedesarrollomobil.ui.tabs.HoraTab
import com.example.clasedesarrollomobil.ui.tabs.ImagenTab
import com.example.clasedesarrollomobil.ui.tabs.RatingTab
import com.example.clasedesarrollomobil.ui.theme.ClaseDesarrolloMobilTheme

class PestanasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClaseDesarrolloMobilTheme {
                PestanasContent(onBack = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PestanasContent(onBack: () -> Unit) {
    val tabs = listOf("Imagen", "Hora", "Rating", "Carga")
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Pestañas",
                onBackToMenu = onBack
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> ImagenTab()
                1 -> HoraTab()
                2 -> RatingTab()
                3 -> CargaTab()
            }
        }
    }
}
