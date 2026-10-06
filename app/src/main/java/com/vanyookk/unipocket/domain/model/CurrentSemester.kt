package com.vanyookk.unipocket.domain.model

data class CurrentSemester(
    val semesterDataId: Int,
    val yearId: Int,
    val yearName: String,
    val periodId: Int,
    val periodName: String
)
