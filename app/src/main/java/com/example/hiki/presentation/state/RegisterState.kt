package com.example.hiki.presentation.state

data class RegisterState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRegistered: Boolean = false,
) {
    fun clearError() = copy(error = null)
}
