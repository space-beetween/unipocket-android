package com.vanyookk.unipocket.data.mapper

import com.vanyookk.unipocket.data.remote.dto.ScheduleDto
import com.vanyookk.unipocket.domain.model.DayOfWeek
import com.vanyookk.unipocket.domain.model.Lesson

fun ScheduleDto.toLesson(): Lesson {
    val subjectName = semesterSubjectName.orEmpty().ifBlank { "Предмет" }

    val startTimeFormatted = beginTime?.let {
        if (it.length >= 5) it.substring(0, 5) else it
    } ?: "00:00"

    return Lesson(
        name = subjectName,
        instructorName = teacher.orEmpty(),
        cabNumber = room.orEmpty(),
        startAt = startTimeFormatted,
        weekId = DayOfWeek.fromId(weekId ?: 1)
    )
}
