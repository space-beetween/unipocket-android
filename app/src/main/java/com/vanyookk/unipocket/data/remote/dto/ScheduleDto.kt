package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ScheduleDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("week")
    val week: String? = null,
    @SerializedName("beginTime")
    val beginTime: String? = null,
    @SerializedName("endTime")
    val endTime: String? = null,
    @SerializedName("lessonType")
    val lessonType: String? = null,
    @SerializedName("semesterSubjectName")
    val semesterSubjectName: String? = null,
    @SerializedName("room")
    val room: String? = null,
    @SerializedName("teacher")
    val teacher: String? = null,
    @SerializedName("weekId")
    val weekId: Int? = null,
    @SerializedName("subjectCode")
    val subjectCode: String? = null
)
