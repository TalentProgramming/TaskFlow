package com.tp.taskflow.core.firebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassroomViewModel @Inject constructor(
    private val remoteConfig: RemoteConfigStore,
    private val crash: CrashReporter
) : ViewModel() {

    val flags = remoteConfig.flags

    init {
        viewModelScope.launch { remoteConfig.refresh() }
    }

    fun testCrash() {
        crash.testCrash()
    }
}
