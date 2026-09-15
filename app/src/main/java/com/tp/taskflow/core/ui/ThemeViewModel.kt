package com.tp.taskflow.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.taskflow.core.settings.ThemeSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val settings: ThemeSettings
) : ViewModel() {

    val darkMode: StateFlow<Boolean> = settings.darkMode.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        false
    )

    fun setDark(enabled: Boolean) {
        viewModelScope.launch { settings.setDark(enabled) }
    }
}
