package com.example.hiki.presentation.event

sealed class ChatUiEvent {
    data class UpdatePrompt(val newPrompt: String) : ChatUiEvent()

    data class SendPrompt(
        val prompt: String,
    ) : ChatUiEvent()

    // show indicator
    data object ShowIndicator : ChatUiEvent()
}