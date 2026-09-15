package com.tp.taskflow.feature.note.domain

import javax.inject.Inject

class UpsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = repository.upsert(note)
}
