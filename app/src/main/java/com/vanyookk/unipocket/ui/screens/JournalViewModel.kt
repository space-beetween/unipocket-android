package com.vanyookk.unipocket.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanyookk.unipocket.data.remote.dto.JournalSummaryDto
import com.vanyookk.unipocket.data.repository.JournalRepository
import com.vanyookk.unipocket.domain.model.Discipline
import com.vanyookk.unipocket.domain.model.Mark
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface JournalUiState {
    data object Loading : JournalUiState
    data class Success(
        val disciplines: List<Discipline>,
        val selectedDiscipline: Discipline?,
        val marks: List<Mark>,
        val summary: JournalSummaryDto?,
        val isJournalLoading: Boolean = false
    ) : JournalUiState
    data class Error(val message: String) : JournalUiState
}

class JournalViewModel(
    private val journalRepository: JournalRepository = JournalRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<JournalUiState>(JournalUiState.Loading)
    val uiState: StateFlow<JournalUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = JournalUiState.Loading

            journalRepository.getCurrentSemesterDataId()
                .onSuccess { semesterDataId ->
                    journalRepository.getSubjects(semesterDataId)
                        .onSuccess { disciplines ->
                            val selected = disciplines.firstOrNull()
                            if (selected != null) {
                                loadJournalForDiscipline(disciplines, selected)
                            } else {
                                _uiState.value = JournalUiState.Success(
                                    disciplines = disciplines,
                                    selectedDiscipline = null,
                                    marks = emptyList(),
                                    summary = null
                                )
                            }
                        }
                        .onFailure { error ->
                            _uiState.value = JournalUiState.Error(
                                error.localizedMessage ?: "Не удалось загрузить список дисциплин"
                            )
                        }
                }
                .onFailure { error ->
                    _uiState.value = JournalUiState.Error(
                        error.localizedMessage ?: "Не удалось определить текущий семестр"
                    )
                }
        }
    }

    fun selectDiscipline(discipline: Discipline) {
        val currentState = _uiState.value
        if (currentState is JournalUiState.Success) {
            if (currentState.selectedDiscipline?.id == discipline.id) return
            loadJournalForDiscipline(currentState.disciplines, discipline)
        }
    }

    private fun loadJournalForDiscipline(disciplines: List<Discipline>, discipline: Discipline) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is JournalUiState.Success) {
                _uiState.value = currentState.copy(selectedDiscipline = discipline, isJournalLoading = true)
            } else {
                _uiState.value = JournalUiState.Success(
                    disciplines = disciplines,
                    selectedDiscipline = discipline,
                    marks = emptyList(),
                    summary = null,
                    isJournalLoading = true
                )
            }

            journalRepository.getJournal(discipline.id)
                .onSuccess { (marks, summary) ->
                    _uiState.value = JournalUiState.Success(
                        disciplines = disciplines,
                        selectedDiscipline = discipline,
                        marks = marks,
                        summary = summary,
                        isJournalLoading = false
                    )
                }
                .onFailure {
                    _uiState.value = JournalUiState.Success(
                        disciplines = disciplines,
                        selectedDiscipline = discipline,
                        marks = emptyList(),
                        summary = null,
                        isJournalLoading = false
                    )
                }
        }
    }
}
