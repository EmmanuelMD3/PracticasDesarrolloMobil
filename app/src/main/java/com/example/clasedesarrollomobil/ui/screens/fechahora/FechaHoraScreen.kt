package com.example.clasedesarrollomobil.ui.screens.fechahora

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun FechaHoraScreen(onBackToMenu: () -> Unit) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val currentDate = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }
    val currentTime = remember {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }
    var selectedDate by rememberSaveable { mutableStateOf("Sin seleccionar") }
    var selectedTime by rememberSaveable { mutableStateOf("Sin seleccionar") }

    DemoScreenScaffold(
        title = "Fecha y hora",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Fecha y hora actual",
            description = "Se obtienen desde el dispositivo usando clases estándar de Android/Kotlin."
        ) {
            Text("Fecha actual: $currentDate")
            Text("Hora actual: $currentTime")
        }

        DemoCard(
            title = "Selector de fecha",
            description = "DatePickerDialog permite elegir una fecha."
        ) {
            Button(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            selectedDate = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            ) {
                Text("Seleccionar fecha")
            }
            Text("Fecha seleccionada: $selectedDate")
        }

        DemoCard(
            title = "Selector de hora",
            description = "TimePickerDialog permite elegir una hora."
        ) {
            Button(
                onClick = {
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            selectedTime = "%02d:%02d".format(hourOfDay, minute)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                }
            ) {
                Text("Seleccionar hora")
            }
            Text("Hora seleccionada: $selectedTime")
        }
    }
}
