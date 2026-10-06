package com.vanyookk.unipocket.data.mapper

import com.vanyookk.unipocket.data.remote.dto.DisciplineDto
import com.vanyookk.unipocket.domain.model.Discipline

fun DisciplineDto.toDiscipline(): Discipline {
    return Discipline(
        id = id ?: 0L,
        code = code.orEmpty(),
        name = name.orEmpty().ifBlank { "Дисциплина" },
        periodId = periodId ?: 0,
        period = period.orEmpty(),
        midAttestation = midAttestation ?: false
    )
}
