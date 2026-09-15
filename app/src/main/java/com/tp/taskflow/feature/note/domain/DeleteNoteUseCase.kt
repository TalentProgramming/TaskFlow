package com.tp.taskflow.feature.note.domain

import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: String) = repository.delete(id)
}
