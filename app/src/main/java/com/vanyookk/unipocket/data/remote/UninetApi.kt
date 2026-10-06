package com.vanyookk.unipocket.data.remote

import com.vanyookk.unipocket.data.remote.dto.CurrentSemesterItemDto
import com.vanyookk.unipocket.data.remote.dto.DisciplineDto
import com.vanyookk.unipocket.data.remote.dto.JournalResponseDto
import com.vanyookk.unipocket.data.remote.dto.ScheduleResponseDto
import com.vanyookk.unipocket.data.remote.dto.StudentResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UninetApi {
    @GET("api/schedule/student/student/me")
    suspend fun getStudentSchedule(): ScheduleResponseDto

    @GET("/api/student/basic/me")
    suspend fun  getStudentInfo(): StudentResponseDto

    @GET("/api/journal/student/semesters/me")
    suspend fun getCurrentSemester(): List<CurrentSemesterItemDto>

    @GET("/api/journal/student/subjects/me")
    suspend fun getSubjects(
        @Query("semesterDataId") semesterDataId: Int
    ): List<DisciplineDto>

    @GET("/api/journal/student/journal/me")
    suspend fun getJournal(
        @Query("semesterSubjectId") semesterSubjectId: Long
    ): JournalResponseDto
}
