package com.tp.taskflow.core.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

data class ClassroomFlags(
    val chatEnabled: Boolean = true,
    val crashEnabled: Boolean = true
)

@Singleton
class RemoteConfigStore @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) {
    private val _flags = MutableStateFlow(ClassroomFlags())
    val flags = _flags.asStateFlow()

    suspend fun refresh() {
        runCatching {
            remoteConfig.fetchAndActivate().await()
            _flags.value = ClassroomFlags(
                chatEnabled = remoteConfig.getBoolean("chat_enabled"),
                crashEnabled = remoteConfig.getBoolean("crash_enabled")
            )
        }
    }
}
