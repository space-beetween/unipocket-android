package com.vanyookk.unipocket.data.mapper

import com.vanyookk.unipocket.data.remote.dto.StudentResponseDto
import com.vanyookk.unipocket.domain.model.Student

fun StudentResponseDto.toStudent(): Student {
    return Student(
        firstName = firstName.orEmpty()
    )
}