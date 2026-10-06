package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class JournalResponseDto(
    @SerializedName("entries")
    val entries: List<MarkDto>? = null,
    @SerializedName("summary")
    val summary: JournalSummaryDto? = null
)

data class JournalSummaryDto(
    @SerializedName("totalGrade")
    val totalGrade: Double? = null,
    @SerializedName("totalAbsent")
    val totalAbsent: Int? = null,
    @SerializedName("totalPresent")
    val totalPresent: Int? = null,
    @SerializedName("totalLate")
    val totalLate: Int? = null,
    @SerializedName("totalLessons")
    val totalLessons: Int? = null,
    @SerializedName("missPercent")
    val missPercent: Int? = null,
    @SerializedName("lectureAttendanceSource")
    val lectureAttendanceSource: String? = null
)
