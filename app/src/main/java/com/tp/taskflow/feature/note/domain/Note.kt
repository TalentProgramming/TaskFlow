package com.tp.taskflow.feature.note.domain

data class Note(
    val id: String,
    val title: String,
    val body: String,
    val updatedAt: Long
)
