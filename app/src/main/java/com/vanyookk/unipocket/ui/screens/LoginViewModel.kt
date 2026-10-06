package com.vanyookk.unipocket.ui.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vanyookk.unipocket.data.remote.RetrofitClient
import com.vanyookk.unipocket.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}


class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun login(username: String, pass: String) {
        val trimmedUsername = username.trim()
        val trimmedPassword = pass.trim()

        if (trimmedUsername.isEmpty() || trimmedPassword.isEmpty()) {
            _uiState.value = LoginUiState.Error("Заполните все поля")
            return
        }

        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            authRepository.login(trimmedUsername, trimmedPassword)
                .onSuccess {
                    _uiState.value = LoginUiState.Success
                }
                .onFailure { error ->
                    _uiState.value = LoginUiState.Error(
                        error.localizedMessage ?: "Не удалось войти. Проверьте логин и пароль.",
                    )
                }
        }
    }

    fun resetError() {
        if (_uiState.value is LoginUiState.Error) {
            _uiState.value = LoginUiState.Idle
        }
    }

    companion object {
        fun provideFactory(
            context: Context,
            authRepository: AuthRepository = AuthRepository(
                authApi = RetrofitClient.authApi,
                context = context.applicationContext,
            ),
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                    return LoginViewModel(authRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}
