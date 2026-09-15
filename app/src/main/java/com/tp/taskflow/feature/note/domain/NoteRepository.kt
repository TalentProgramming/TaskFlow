package com.tp.taskflow.feature.note.domain

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observe(query: String): Flow<List<Note>>
    suspend fun upsert(note: Note)
    suspend fun delete(id: String)
}
