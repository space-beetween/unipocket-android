package com.vanyookk.unipocket.domain.model

data class Discipline(
    val id: Long,
    val code: String,
    val name: String,
    val periodId: Int,
    val period: String,
    val midAttestation: Boolean
)
