package com.example.hiki.presentation.state

import com.example.hiki.domain.model.entity.Chat

data class ChatState(
    val chatList: MutableList<Chat> = mutableListOf(),
    var prompt: String = "",
    val showIndicator: Boolean = false,
)