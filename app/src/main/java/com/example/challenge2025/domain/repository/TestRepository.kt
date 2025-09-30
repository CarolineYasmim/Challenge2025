package com.example.challenge2025.domain.repository

import com.example.challenge2025.domain.model.tests.TestItem
import com.example.challenge2025.domain.util.Resource

interface TestRepository {
    suspend fun getTestsWithUserStatus(): Resource<List<TestItem>>
}