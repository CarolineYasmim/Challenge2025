package com.example.challenge2025.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge2025.domain.model.tests.TestItem
import com.example.challenge2025.domain.repository.TestRepository
import com.example.challenge2025.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val testRepository: TestRepository
) : ViewModel() {

    private val _testsState = MutableStateFlow<Resource<List<TestItem>>>(Resource.Loading())
    val testsState = _testsState.asStateFlow()

    init {
        loadTests()
    }

    fun loadTests() {
        viewModelScope.launch {
            _testsState.value = Resource.Loading()
            _testsState.value = testRepository.getTestsWithUserStatus()
        }
    }
}