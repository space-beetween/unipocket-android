package com.vanyookk.unipocket.domain.model

data class Mark(
    val id: Long,
    val date: String,
    val lesson: String,
    val mark: String,
    val grade: Double?,
    val comment: String?
)
