package com.vanyookk.unipocket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DisciplineDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("periodId")
    val periodId: Int? = null,
    @SerializedName("period")
    val period: String? = null,
    @SerializedName("midAttestation")
    val midAttestation: Boolean? = null
)
