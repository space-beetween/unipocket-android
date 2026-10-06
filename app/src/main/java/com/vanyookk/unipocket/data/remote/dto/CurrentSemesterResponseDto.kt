package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrentSemesterResponseDto(
    @SerializedName("semesters")
    val semesters: List<SemesterDto>? = null
)
{
    fun getCurrentSemester(): SemesterDto? {
        return semesters?.firstOrNull { it.isCurrent == true } ?: semesters?.firstOrNull()
    }
}
