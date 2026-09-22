package com.tp.taskflow.feature.settings.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _notifications = MutableStateFlow(true)
    val notifications = _notifications.asStateFlow()

    fun setNotifications(enabled: Boolean) {
        _notifications.value = enabled
    }
}
