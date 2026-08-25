package com.example.clasedesarrollomobil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.clasedesarrollomobil.data.repository.AccessLogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class LoginUiState(
    val user: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val showExitDialog: Boolean = false,
    val isLoggedIn: Boolean = false
)

class LoginViewModel(
    private val accessLogRepository: AccessLogRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUserChanged(user: String) {
        _uiState.update { it.copy(user = user, errorMessage = null) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun onLoginClicked() {
        val currentState = _uiState.value
        if (currentState.user == "admin" && currentState.password == "1234") {
            viewModelScope.launch {
                val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
                accessLogRepository.saveAccess(currentState.user, date)
                _uiState.update {
                    it.copy(
                        errorMessage = null,
                        successMessage = "Acceso correcto",
                        isLoggedIn = true
                    )
                }
            }
        } else {
            _uiState.update {
                it.copy(
                    errorMessage = "Usuario o contraseña incorrectos",
                    successMessage = null,
                    isLoggedIn = false
                )
            }
        }
    }

    fun showExitDialog() {
        _uiState.update { it.copy(showExitDialog = true) }
    }

    fun hideExitDialog() {
        _uiState.update { it.copy(showExitDialog = false) }
    }

    fun clearLoginEvent() {
        _uiState.update { it.copy(successMessage = null, isLoggedIn = false) }
    }
}

class LoginViewModelFactory(
    private val accessLogRepository: AccessLogRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(accessLogRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
