package com.vanyookk.unipocket.domain.model

data class Event(
    val id: String,
    val title: String,                  // Название: "Хакатон Yandex", "Киновечер"
    val description: String,            // Описание мероприятия
    val date: String,                   // Для демки проще использовать String (например, "12 Октября, 18:00")
    val location: String,               // Локация: "Аудитория 305" или "Zoom"
    val category: String,               // Категория: "Спорт", "Учеба", "Развлечения"
    val organizer: String,              // "Студсовет", "Кафедра ПО"
    val imageUrl: String? = null,       // Ссылка на обложку (или res ID)
    val isRegistered: Boolean = false   // Зарегистрирован ли текущий юзер (для интерактивной кнопки)
)
