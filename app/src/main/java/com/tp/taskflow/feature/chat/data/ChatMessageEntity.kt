package com.tp.taskflow.feature.chat.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val senderId: String,
    val text: String,
    val createdAt: Long
)
