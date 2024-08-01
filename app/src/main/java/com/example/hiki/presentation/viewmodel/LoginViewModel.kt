package com.example.hiki.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiki.domain.repository.UserRepository
import com.example.hiki.presentation.event.LoginUiEvent
import com.example.hiki.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.UpdateEmail -> {
                _loginState.update { it.clearError().copy(email = event.email) }
            }

            is LoginUiEvent.UpdatePassword -> {
                _loginState.update { it.clearError().copy(password = event.password) }
            }

            is LoginUiEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        _loginState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(_loginState.value.email)
            if (user != null && user.password == _loginState.value.password) {
                _loginState.update { it.copy(isLoggedIn = true, isLoading = false, error = null) }
            } else {
                val errorMessage = if (user == null) {
                    "Invalid email"
                } else {
                    "Invalid password"
                }
                _loginState.update { it.copy(error = errorMessage, isLoading = false) }
            }

        }
    }
}