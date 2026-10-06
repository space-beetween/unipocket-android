package com.vanyookk.unipocket.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanyookk.unipocket.data.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MainUiState {
    object Loading : MainUiState
    data class Success(val studentData: Unit = Unit) : MainUiState
    data class Error(val message: String) : MainUiState
}

class MainViewModel(
    private val studentRepository: StudentRepository = StudentRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadStudentData()
    }

    fun loadStudentData() {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            try {
                studentRepository.getStudentInfo()
                _uiState.value = MainUiState.Success()
            } catch (e: Exception) {
                _uiState.value = MainUiState.Error(
                    e.localizedMessage ?: "Ошибка загрузки данных"
                )
            }
        }
    }
}