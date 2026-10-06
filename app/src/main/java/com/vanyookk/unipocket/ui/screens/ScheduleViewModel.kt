package com.vanyookk.unipocket.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanyookk.unipocket.data.repository.ScheduleRepository
import com.vanyookk.unipocket.data.repository.StudentRepository
import com.vanyookk.unipocket.domain.model.DayOfWeek
import com.vanyookk.unipocket.domain.model.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

data class WeekDayModel(
    val dayOfWeek: DayOfWeek,
    val date: LocalDate,
    val dayNumber: Int,
    val shortName: String
)

sealed interface ScheduleUiState {
    data object Loading : ScheduleUiState
    data class Success(
        val lessons: List<Lesson>,
        val selectedDay: DayOfWeek,
        val weekDays: List<WeekDayModel>,
        val totalLessonsCount: Int
    ) : ScheduleUiState
    data class Error(val message: String) : ScheduleUiState
}

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository = ScheduleRepository(),
    studentRepository: StudentRepository = StudentRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScheduleUiState>(ScheduleUiState.Loading)
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    val student = StudentRepository.cachedStudent

    private var allLessons: List<Lesson> = emptyList()
    private var currentWeekDays: List<WeekDayModel> = emptyList()

    init {
        loadSchedule()
    }

    fun loadSchedule() {
        viewModelScope.launch {
            _uiState.value = ScheduleUiState.Loading

            val today = LocalDate.now()
            val monday = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))

            currentWeekDays = (0..6).map { i ->
                val date = monday.plusDays(i.toLong())
                val dayOfWeek = DayOfWeek.fromId(i + 1)
                WeekDayModel(
                    dayOfWeek = dayOfWeek,
                    date = date,
                    dayNumber = date.dayOfMonth,
                    shortName = dayOfWeek.shortName
                )
            }

            val todayDayId = today.dayOfWeek.value
            val initialSelectedDay = DayOfWeek.fromId(todayDayId)

            scheduleRepository.getSchedule()
                .onSuccess { lessons ->
                    allLessons = lessons
                    updateStateForSelectedDay(initialSelectedDay)
                }
                .onFailure { error ->
                    _uiState.value = ScheduleUiState.Error(
                        message = error.localizedMessage ?: "Не удалось загрузить расписание"
                    )
                }
        }
    }

    fun selectDay(dayOfWeek: DayOfWeek) {
        updateStateForSelectedDay(dayOfWeek)
    }

    private fun updateStateForSelectedDay(dayOfWeek: DayOfWeek) {
        val filteredLessons = allLessons
            .filter { it.weekId == dayOfWeek }
            .sortedBy { it.startAt }

        _uiState.value = ScheduleUiState.Success(
            lessons = filteredLessons,
            selectedDay = dayOfWeek,
            weekDays = currentWeekDays,
            totalLessonsCount = allLessons.size
        )
    }
}
