package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SemesterDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("startDate")
    val startDate: String? = null,
    @SerializedName("endDate")
    val endDate: String? = null,
    @SerializedName("isCurrent")
    val isCurrent: Boolean? = null
)
