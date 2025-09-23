package com.example.challenge2025.viewmodel

import androidx.lifecycle.ViewModel
import com.example.challenge2025.model.data.MockData
import com.example.challenge2025.model.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow(MockData.currentUser)
    val currentUser = _currentUser.asStateFlow()

    // Futuramente, esta função receberá os novos dados da tela de edição
    // e os salvará no backend, atualizando o StateFlow em seguida.
    fun updateUser(updatedUser: User) {
        _currentUser.value = updatedUser
    }
}