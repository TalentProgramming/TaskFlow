package com.tp.taskflow.feature.chat.domain

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun observe(): Flow<List<ChatMessage>>
    suspend fun send(text: String, senderId: String)
}
