package com.vanyookk.unipocket.data.mapper

import com.vanyookk.unipocket.data.remote.dto.CurrentSemesterItemDto
import com.vanyookk.unipocket.domain.model.CurrentSemester

fun CurrentSemesterItemDto.toCurrentSemester(): CurrentSemester {
    return CurrentSemester(
        semesterDataId = semesterDataId ?: 0,
        yearId = yearId ?: 0,
        yearName = yearName.orEmpty(),
        periodId = periodId ?: 0,
        periodName = periodName.orEmpty()
    )
}
