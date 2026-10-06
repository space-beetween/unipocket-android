package com.vanyookk.unipocket.data.repository

import com.vanyookk.unipocket.data.mapper.toStudent
import com.vanyookk.unipocket.data.remote.RetrofitClient
import com.vanyookk.unipocket.data.remote.UninetApi
import com.vanyookk.unipocket.domain.model.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudentRepository(
    private val api: UninetApi = RetrofitClient.api
) {
    companion object {
        private val _cachedStudent = MutableStateFlow<Student?>(null)
        val cachedStudent: StateFlow<Student?> = _cachedStudent.asStateFlow()
    }

    suspend fun getStudentInfo(): Result<Student> {
        return try {
            val response = api.getStudentInfo()
            val student = response.toStudent()
            _cachedStudent.value = student
            Result.success(student)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}