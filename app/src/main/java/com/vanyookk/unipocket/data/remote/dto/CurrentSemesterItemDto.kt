package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrentSemesterItemDto(
    @SerializedName("semesterDataId")
    val semesterDataId: Int? = null,
    @SerializedName("yearId")
    val yearId: Int? = null,
    @SerializedName("yearName")
    val yearName: String? = null,
    @SerializedName("periodId")
    val periodId: Int? = null,
    @SerializedName("periodName")
    val periodName: String? = null
)
