package com.vanyookk.unipocket.domain.model

data class Semester(
    val id: Int,
    val name: String,
    val startDate: String,
    val endDate: String,
    val isCurrent: Boolean
)
