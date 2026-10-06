package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MarkDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("lesson")
    val lesson: String? = null,
    @SerializedName("mark")
    val mark: String? = null,
    @SerializedName("grade")
    val grade: Double? = null,
    @SerializedName("comment")
    val comment: String? = null
)
