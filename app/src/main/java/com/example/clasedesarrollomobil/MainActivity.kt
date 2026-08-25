package com.example.clasedesarrollomobil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.clasedesarrollomobil.navigation.AppNavigation
import com.example.clasedesarrollomobil.ui.theme.ClaseDesarrolloMobilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClaseDesarrolloMobilTheme {
                AppNavigation()
            }
        }
    }
}
