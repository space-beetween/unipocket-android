package com.vanyookk.unipocket.data.repository

import com.vanyookk.unipocket.data.mapper.toDiscipline
import com.vanyookk.unipocket.data.mapper.toMark
import com.vanyookk.unipocket.data.remote.RetrofitClient
import com.vanyookk.unipocket.data.remote.UninetApi
import com.vanyookk.unipocket.data.remote.dto.JournalSummaryDto
import com.vanyookk.unipocket.domain.model.Discipline
import com.vanyookk.unipocket.domain.model.Mark

class JournalRepository(
    private val api: UninetApi = RetrofitClient.api
) {
    suspend fun getCurrentSemesterDataId(): Result<Int> {
        return try {
            val semesters = api.getCurrentSemester()
            val current = semesters.firstOrNull() ?: semesters.firstOrNull { it.semesterDataId != null }
            val id = current?.semesterDataId ?: 0
            if (id != 0) {
                Result.success(id)
            } else {
                Result.failure(Exception("Текущий семестр не найден"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSubjects(semesterDataId: Int): Result<List<Discipline>> {
        return try {
            val dtos = api.getSubjects(semesterDataId)
            val disciplines = dtos.map { it.toDiscipline() }
            Result.success(disciplines)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getJournal(semesterSubjectId: Long): Result<Pair<List<Mark>, JournalSummaryDto?>> {
        return try {
            val response = api.getJournal(semesterSubjectId)
            val marks = response.entries.orEmpty().map { it.toMark() }
            Result.success(Pair(marks, response.summary))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
