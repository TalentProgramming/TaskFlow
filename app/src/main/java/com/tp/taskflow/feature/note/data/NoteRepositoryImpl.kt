package com.tp.taskflow.feature.note.data

import com.tp.taskflow.feature.note.domain.Note
import com.tp.taskflow.feature.note.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override fun observe(query: String): Flow<List<Note>> {
        val source = if (query.isBlank()) dao.observeAll() else dao.search(query)
        return source
            .onStart { seedIfEmpty() }
            .map { rows -> rows.map { it.toDomain() } }
    }

    override suspend fun upsert(note: Note) {
        dao.upsert(note.toEntity())
    }

    override suspend fun delete(id: String) {
        dao.delete(id)
    }

    private suspend fun seedIfEmpty() {
        if (dao.count() > 0) return
        val now = System.currentTimeMillis()
        listOf(
            Note(UUID.randomUUID().toString(), "Welcome", "Room keeps this note after you kill the app.", now),
            Note(UUID.randomUUID().toString(), "Lab tip", "Search and sort use the DAO, not the ViewModel.", now - 1_000)
        ).forEach { dao.upsert(it.toEntity()) }
    }
}
