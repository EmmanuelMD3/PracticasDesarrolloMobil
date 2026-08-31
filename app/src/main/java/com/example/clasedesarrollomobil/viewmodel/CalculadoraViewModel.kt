package com.example.clasedesarrollomobil.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

data class CalculadoraUiState(
    val expression: String = "",
    val result: String = "0",
    val errorMessage: String? = null
)

class CalculadoraViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculadoraUiState())
    val uiState: StateFlow<CalculadoraUiState> = _uiState.asStateFlow()

    fun append(value: String) {
        _uiState.update {
            it.copy(
                expression = it.expression + value,
                errorMessage = null
            )
        }
    }

    fun clear() {
        _uiState.value = CalculadoraUiState()
    }

    fun deleteLast() {
        _uiState.update {
            it.copy(
                expression = it.expression.dropLast(1),
                errorMessage = null
            )
        }
    }

    fun toggleSign() {
        _uiState.update {
            if (it.expression.startsWith("-")) {
                it.copy(expression = it.expression.drop(1))
            } else {
                it.copy(expression = "-${it.expression}")
            }
        }
    }

    fun calculate() {
        val expression = _uiState.value.expression
        if (expression.isBlank()) return

        val result = runCatching {
            ScientificExpressionParser(expression).parse()
        }.getOrElse {
            _uiState.update { current ->
                current.copy(errorMessage = "Operación no válida")
            }
            return
        }

        if (result.isNaN() || result.isInfinite()) {
            _uiState.update { it.copy(errorMessage = "Operación no válida") }
        } else {
            _uiState.update {
                it.copy(
                    result = formatResult(result),
                    errorMessage = null
                )
            }
        }
    }

    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            "%.8f".format(value).trimEnd('0').trimEnd('.')
        }
    }
}

private class ScientificExpressionParser(
    private val source: String
) {
    private var index = 0

    // Parser recursivo: expresión -> término -> potencia -> factor.
    fun parse(): Double {
        val result = parseExpression()
        skipSpaces()
        if (index < source.length) error("Unexpected input")
        return result
    }

    private fun parseExpression(): Double {
        var value = parseTerm()
        while (true) {
            skipSpaces()
            value = when {
                match('+') -> value + parseTerm()
                match('-') -> value - parseTerm()
                else -> return value
            }
        }
    }

    private fun parseTerm(): Double {
        var value = parsePower()
        while (true) {
            skipSpaces()
            value = when {
                match('*') -> value * parsePower()
                match('/') -> {
                    val divisor = parsePower()
                    if (divisor == 0.0) error("Division by zero")
                    value / divisor
                }
                else -> return value
            }
        }
    }

    private fun parsePower(): Double {
        var value = parseFactor()
        skipSpaces()
        if (match('^')) {
            value = value.pow(parsePower())
        }
        return value
    }

    private fun parseFactor(): Double {
        skipSpaces()
        var value = when {
            match('+') -> parseFactor()
            match('-') -> -parseFactor()
            match('(') -> {
                val inner = parseExpression()
                if (!match(')')) error("Missing parenthesis")
                inner
            }
            peekLetter() -> parseFunctionOrConstant()
            else -> parseNumber()
        }

        skipSpaces()
        while (match('%')) {
            value /= 100.0
        }
        return value
    }

    private fun parseFunctionOrConstant(): Double {
        val name = buildString {
            while (index < source.length && source[index].isLetter()) {
                append(source[index])
                index++
            }
        }.lowercase()

        if (name == "pi") return PI

        if (!match('(')) error("Function requires parenthesis")
        val argument = parseExpression()
        if (!match(')')) error("Missing parenthesis")

        return when (name) {
            "sin" -> sin(Math.toRadians(argument))
            "cos" -> cos(Math.toRadians(argument))
            "tan" -> tan(Math.toRadians(argument))
            "sqrt" -> if (argument >= 0) sqrt(argument) else error("Invalid root")
            "log" -> if (argument > 0) log10(argument) else error("Invalid log")
            "ln" -> if (argument > 0) ln(argument) else error("Invalid ln")
            else -> error("Unknown function")
        }
    }

    private fun parseNumber(): Double {
        skipSpaces()
        val start = index
        while (index < source.length && (source[index].isDigit() || source[index] == '.')) {
            index++
        }
        if (start == index) error("Number expected")
        return source.substring(start, index).toDouble()
    }

    private fun peekLetter(): Boolean {
        skipSpaces()
        return index < source.length && source[index].isLetter()
    }

    private fun match(char: Char): Boolean {
        skipSpaces()
        return if (index < source.length && source[index] == char) {
            index++
            true
        } else {
            false
        }
    }

    private fun skipSpaces() {
        while (index < source.length && source[index].isWhitespace()) {
            index++
        }
    }
}
