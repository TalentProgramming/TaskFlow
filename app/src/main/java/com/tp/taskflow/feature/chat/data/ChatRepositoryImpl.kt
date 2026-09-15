package com.tp.taskflow.feature.chat.data

import com.tp.taskflow.feature.chat.domain.ChatMessage
import com.tp.taskflow.feature.chat.domain.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val dao: ChatDao
) : ChatRepository {
    override fun observe(): Flow<List<ChatMessage>> =
        dao.observe().map { rows ->
            rows.map { ChatMessage(it.id, it.senderId, it.text, it.createdAt) }
        }

    override suspend fun send(text: String, senderId: String) {
        dao.insert(
            ChatMessageEntity(
                id = UUID.randomUUID().toString(),
                senderId = senderId,
                text = text,
                createdAt = System.currentTimeMillis()
            )
        )
    }
}
