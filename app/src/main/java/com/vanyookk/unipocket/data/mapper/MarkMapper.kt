package com.vanyookk.unipocket.data.mapper

import com.vanyookk.unipocket.data.remote.dto.MarkDto
import com.vanyookk.unipocket.domain.model.Mark

fun MarkDto.toMark(): Mark {
    return Mark(
        id = id ?: 0L,
        date = date.orEmpty(),
        lesson = lesson.orEmpty().ifBlank { "Дисциплина" },
        mark = mark.orEmpty().ifBlank { "Оценка" },
        grade = grade,
        comment = comment
    )
}
