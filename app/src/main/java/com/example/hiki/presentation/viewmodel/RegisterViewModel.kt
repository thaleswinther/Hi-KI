package com.example.hiki.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiki.domain.model.entity.User
import com.example.hiki.domain.repository.UserRepository
import com.example.hiki.presentation.event.RegisterUiEvent
import com.example.hiki.presentation.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.UpdateEmail -> {
                _registerState.update { it.clearError().copy(email = event.email) }
            }
            is RegisterUiEvent.UpdateUsername -> {
                _registerState.update { it.clearError().copy(username = event.username) }
            }
            is RegisterUiEvent.UpdatePassword -> {
                _registerState.update { it.clearError().copy(password = event.password) }
            }
            is RegisterUiEvent.Register -> {
                register()
            }
        }
    }

    private fun register() {
        val state = _registerState.value
        if (state.email.isBlank() || state.username.isBlank() || state.password.isBlank()) {
            _registerState.update { it.copy(error = "All fields are required") }
            return
        }

        _registerState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val existingUser = userRepository.getUserByEmail(state.email)
            if (existingUser != null) {
                _registerState.update { it.copy(error = "User already exists", isLoading = false) }
            } else {
                val user = User(
                    email = state.email,
                    username = state.username,
                    password = state.password
                )
                userRepository.insert(user)
                _registerState.update { it.copy(isRegistered = true, isLoading = false) }
            }
        }
    }
}