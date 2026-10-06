package com.vanyookk.unipocket.ui.screens

import androidx.lifecycle.ViewModel
import com.vanyookk.unipocket.domain.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventsViewModel : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(
        listOf(
            Event(
                id = "1",
                title = "MOVIE DAY",
                description = "Приглашаем тебя на Movie Day — уютный киновечер, где можно отвлечься от учебы, насладиться классным фильмом и провести время в отличной компании прямо в сердце КБТУ",
                date = "18 Октября, 18:30",
                location = "Independence Hall",
                category = "Развлечения",
                organizer = "StudEX",
                isRegistered = false
            )
        )
    )
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    fun toggleRegistration(eventId: String) {
        _events.value = _events.value.map { event ->
            if (event.id == eventId) {
                event.copy(isRegistered = !event.isRegistered)
            } else {
                event
            }
        }
    }
}
