package com.example.hiki.presentation.event

sealed class RegisterUiEvent {
    data class UpdateEmail(val email: String) : RegisterUiEvent()
    data class UpdateUsername(val username: String) : RegisterUiEvent()
    data class UpdatePassword(val password: String) : RegisterUiEvent()
    data object Register : RegisterUiEvent()
}