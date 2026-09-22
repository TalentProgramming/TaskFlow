package com.tp.taskflow.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.taskflow.core.firebase.FirebaseAuthClient
import com.tp.taskflow.feature.chat.domain.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val auth: FirebaseAuthClient
) : ViewModel() {

    val messages = repository.observe().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    private val _draft = MutableStateFlow("")
    val draft = _draft.asStateFlow()

    fun onDraftChange(value: String) {
        _draft.value = value
    }

    fun send() {
        val text = _draft.value.trim()
        if (text.isEmpty()) return
        viewModelScope.launch {
            repository.send(text, senderId = auth.uid ?: "anonymous")
            _draft.value = ""
        }
    }
}
