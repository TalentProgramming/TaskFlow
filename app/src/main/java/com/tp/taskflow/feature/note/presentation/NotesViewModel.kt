package com.tp.taskflow.feature.note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.taskflow.feature.note.domain.DeleteNoteUseCase
import com.tp.taskflow.feature.note.domain.Note
import com.tp.taskflow.feature.note.domain.ObserveNotesUseCase
import com.tp.taskflow.feature.note.domain.UpsertNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NotesViewModel @Inject constructor(
    observeNotes: ObserveNotesUseCase,
    private val upsertNote: UpsertNoteUseCase,
    private val deleteNote: DeleteNoteUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _draftTitle = MutableStateFlow("")
    val draftTitle: StateFlow<String> = _draftTitle.asStateFlow()

    private val _draftBody = MutableStateFlow("")
    val draftBody: StateFlow<String> = _draftBody.asStateFlow()

    val notes: StateFlow<List<Note>> = _query
        .flatMapLatest { text -> observeNotes(text) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onQueryChange(value: String) {
        _query.value = value
    }

    fun onDraftTitleChange(value: String) {
        _draftTitle.value = value
    }

    fun onDraftBodyChange(value: String) {
        _draftBody.value = value
    }

    fun saveDraft() {
        val title = _draftTitle.value.trim()
        val body = _draftBody.value.trim()
        if (title.isEmpty()) return
        viewModelScope.launch {
            upsertNote(
                Note(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    body = body,
                    updatedAt = System.currentTimeMillis()
                )
            )
            _draftTitle.value = ""
            _draftBody.value = ""
        }
    }

    fun delete(id: String) {
        viewModelScope.launch { deleteNote(id) }
    }
}
