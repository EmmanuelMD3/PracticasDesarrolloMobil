package com.example.clasedesarrollomobil.ui.screens.barras

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clasedesarrollomobil.ui.components.AppTopBar
import com.example.clasedesarrollomobil.ui.components.CircularProgressIndicatorSection
import com.example.clasedesarrollomobil.ui.components.DemoDateSection
import com.example.clasedesarrollomobil.ui.components.DemoHeader
import com.example.clasedesarrollomobil.ui.components.LinearProgressIndicatorSection
import com.example.clasedesarrollomobil.ui.components.ProgressBarSection
import com.example.clasedesarrollomobil.ui.components.RatingBarSection
import com.example.clasedesarrollomobil.ui.components.SeekBarSection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BarrasIndicadoresScreen(onBackToMenu: () -> Unit) {
    var progress by remember { mutableStateOf(0) }
    var isProgressRunning by remember { mutableStateOf(false) }
    var seekValue by remember { mutableStateOf(50) }
    var rating by remember { mutableStateOf(3f) }
    var showCircularIndicator by remember { mutableStateOf(true) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val currentDateTime = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Barras e Indicadores",
                onBackToMenu = onBackToMenu
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DemoHeader()

            ProgressBarSection(
                progress = progress,
                isProgressRunning = isProgressRunning,
                onStartProgress = {
                    progress = 0
                    isProgressRunning = true

                    // Simulación de progreso sin bloquear el hilo principal.
                    coroutineScope.launch {
                        delay(250)
                        for (value in 5..100 step 5) {
                            progress = value
                            delay(180)
                        }
                        isProgressRunning = false
                        snackbarHostState.showSnackbar("Proceso completado")
                    }
                }
            )

            SeekBarSection(
                seekValue = seekValue,
                onValueChanged = { seekValue = it }
            )

            RatingBarSection(
                rating = rating,
                onRatingChanged = { rating = it }
            )

            LinearProgressIndicatorSection(progress = progress)

            CircularProgressIndicatorSection(
                showCircularIndicator = showCircularIndicator,
                onVisibilityChanged = { showCircularIndicator = it }
            )

            DemoDateSection(currentDateTime = currentDateTime)
        }
    }
}
