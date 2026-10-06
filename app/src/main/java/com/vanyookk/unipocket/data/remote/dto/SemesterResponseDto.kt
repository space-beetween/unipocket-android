package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SemesterResponseDto(
    @SerializedName("semesters")
    val semesters: List<SemesterDto>? = null
)
