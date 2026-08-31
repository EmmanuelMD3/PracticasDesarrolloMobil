package com.example.clasedesarrollomobil.ui.screens.calculadora

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import com.example.clasedesarrollomobil.viewmodel.CalculadoraViewModel

@Composable
fun CalculadoraScreen(
    onBackToMenu: () -> Unit,
    viewModel: CalculadoraViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    DemoScreenScaffold(
        title = "Calculadora científica",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Expresión",
            description = "La lógica está separada en CalculadoraViewModel con un parser seguro."
        ) {
            Text(
                text = uiState.expression.ifBlank { "0" },
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Resultado: ${uiState.result}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        val rows = listOf(
            listOf("sin(", "cos(", "tan(", "sqrt("),
            listOf("log(", "ln(", "pi", "^"),
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", ".", "%", "+"),
            listOf("(", ")", "+/-", "=")
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { label ->
                        CalculatorButton(
                            label = label,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                when (label) {
                                    "=" -> viewModel.calculate()
                                    "+/-" -> viewModel.toggleSign()
                                    else -> viewModel.append(label)
                                }
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = viewModel::deleteLast,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Borrar")
                }
                OutlinedButton(
                    onClick = viewModel::clear,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Limpiar todo")
                }
            }
        }
    }
}

@Composable
private fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(vertical = 2.dp)
    ) {
        Text(label)
    }
}
