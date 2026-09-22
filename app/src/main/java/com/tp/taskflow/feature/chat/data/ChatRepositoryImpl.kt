package com.tp.taskflow.feature.chat.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tp.taskflow.core.firebase.CrashReporter
import com.tp.taskflow.core.firebase.FirebaseAuthClient
import com.tp.taskflow.feature.chat.domain.ChatMessage
import com.tp.taskflow.feature.chat.domain.ChatRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dao: ChatDao,
    private val auth: FirebaseAuthClient,
    private val crash: CrashReporter
) : ChatRepository {

    override fun observe(): Flow<List<ChatMessage>> = callbackFlow {
        val registration = firestore.collection("messages")
            .orderBy("createdAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    crash.record(error)
                    return@addSnapshotListener
                }
                val rows = snapshot?.documents.orEmpty().map { doc ->
                    ChatMessage(
                        id = doc.id,
                        senderId = doc.getString("senderId").orEmpty(),
                        text = doc.getString("text").orEmpty(),
                        createdAt = doc.getLong("createdAt") ?: 0L
                    )
                }
                trySend(rows)
            }
        awaitClose { registration.remove() }
    }

    override suspend fun send(text: String, senderId: String) {
        val uid = auth.uid ?: senderId
        val payload = mapOf(
            "senderId" to uid,
            "text" to text,
            "createdAt" to System.currentTimeMillis()
        )
        try {
            firestore.collection("messages").add(payload).await()
        } catch (error: Exception) {
            crash.record(error)
            dao.insert(
                ChatMessageEntity(
                    id = UUID.randomUUID().toString(),
                    senderId = uid,
                    text = text,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }
}
