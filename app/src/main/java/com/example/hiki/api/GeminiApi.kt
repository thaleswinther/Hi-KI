package com.example.hiki.api

import com.example.hiki.domain.model.entity.Chat
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GeminiApi {
    private const val api_key = "AIzaSyBQ2VvBlRkktZRVdybk60XgGDTKrF-tJA8"

    suspend fun getResponse(prompt: String): Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = api_key,
        )
        return try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }

            Chat(
                prompt = response.text ?: "error",
                isFromUser = false,
            )
        } catch (e: Exception) {
            Chat(
                prompt = e.message ?: "error",
                isFromUser = false,
            )
        }
    }
}