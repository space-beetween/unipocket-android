package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ScheduleResponseDto(
    @SerializedName("schedules")
    val schedules: List<ScheduleDto>? = null,
    @SerializedName("currentYearId")
    val currentYearId: Int? = null,
    @SerializedName("currentPeriodId")
    val currentPeriodId: Int? = null,
    @SerializedName("scheduleHidden")
    val scheduleHidden: Boolean? = null
)
