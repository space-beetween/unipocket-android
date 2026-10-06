package com.vanyookk.unipocket.domain.model

enum class DayOfWeek(val id: Int, val shortName: String, val fullName: String) {
    MONDAY(1, "Пн", "Понедельник"),
    TUESDAY(2, "Вт", "Вторник"),
    WEDNESDAY(3, "Ср", "Среда"),
    THURSDAY(4, "Чт", "Четверг"),
    FRIDAY(5, "Пт", "Пятница"),
    SATURDAY(6, "Сб", "Суббота"),
    SUNDAY(7, "Вс", "Воскресенье");

    companion object {
        fun fromId(id: Int): DayOfWeek {
            return entries.find { it.id == id } ?: MONDAY
        }
    }
}