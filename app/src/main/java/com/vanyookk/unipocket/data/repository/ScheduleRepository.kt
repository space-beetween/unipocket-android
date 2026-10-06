package com.vanyookk.unipocket.data.repository

import com.vanyookk.unipocket.data.mapper.toLesson
import com.vanyookk.unipocket.data.remote.RetrofitClient
import com.vanyookk.unipocket.data.remote.UninetApi
import com.vanyookk.unipocket.domain.model.Lesson

class ScheduleRepository(
    private val api: UninetApi = RetrofitClient.api
) {
    suspend fun getSchedule(): Result<List<Lesson>> {
        return try {
            val response = api.getStudentSchedule()
            val lessons = response.schedules.orEmpty().map { it.toLesson() }
            Result.success(lessons)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
