package com.vanyookk.unipocket.data.mapper

import com.vanyookk.unipocket.data.remote.dto.SemesterDto
import com.vanyookk.unipocket.domain.model.Semester

fun SemesterDto.toSemester(): Semester {
    return Semester(
        id = id ?: 0,
        name = name.orEmpty().ifBlank { "Семестр" },
        startDate = startDate.orEmpty(),
        endDate = endDate.orEmpty(),
        isCurrent = isCurrent ?: false
    )
}
