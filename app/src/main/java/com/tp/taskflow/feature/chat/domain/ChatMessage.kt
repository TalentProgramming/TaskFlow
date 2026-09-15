package com.tp.taskflow.feature.chat.domain

data class ChatMessage(
    val id: String,
    val senderId: String,
    val text: String,
    val createdAt: Long
)
