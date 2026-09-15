package com.tp.taskflow.feature.note.data

import com.tp.taskflow.feature.note.domain.Note

fun NoteEntity.toDomain(): Note = Note(id, title, body, updatedAt)

fun Note.toEntity(): NoteEntity = NoteEntity(id, title, body, updatedAt)
