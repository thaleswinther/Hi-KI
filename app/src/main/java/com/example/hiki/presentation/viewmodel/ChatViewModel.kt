package com.example.hiki.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiki.api.GeminiApi
import com.example.hiki.domain.model.entity.Chat
import com.example.hiki.presentation.event.ChatUiEvent
import com.example.hiki.presentation.state.ChatState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty()) {
                    addPrompt(event.prompt)
                    getResponse(event.prompt)
                }
            }

            is ChatUiEvent.UpdatePrompt -> {
                _chatState.update {
                    it.copy(prompt = event.newPrompt)
                }
            }

            is ChatUiEvent.ShowIndicator -> {
                showIndicator()
            }
        }
    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            val chat = GeminiApi.getResponse(prompt)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    },
                    showIndicator = false,
                )
            }
        }

    }


    private fun addPrompt(prompt: String) {
        _chatState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(0, Chat(prompt, true))
                },
                prompt = "",
            )
        }
    }

    private fun showIndicator() {
        _chatState.update {
            it.copy(
                showIndicator = true
            )
        }
    }
}