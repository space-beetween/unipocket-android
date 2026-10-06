package com.vanyookk.unipocket.domain.model

data class Lesson(
    val name: String,
    val instructorName: String,
    val cabNumber: String,
    val startAt: String,
    val weekId: DayOfWeek = DayOfWeek.MONDAY
)