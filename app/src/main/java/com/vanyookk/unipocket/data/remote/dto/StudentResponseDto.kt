package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StudentResponseDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("lastName")
    val lastName: String? = null,
)
