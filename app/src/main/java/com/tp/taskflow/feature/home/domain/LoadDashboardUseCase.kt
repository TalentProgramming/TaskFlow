package com.tp.taskflow.feature.home.domain

import android.util.Log
import com.tp.taskflow.core.common.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.cancellation.CancellationException
import kotlin.system.measureTimeMillis

import javax.inject.Inject

class LoadDashboardUseCase @Inject constructor(
    private val repository: DashboardRepository
) {
    suspend operator fun invoke(): Resource<Dashboard> {
        return try {
            var dashboard: Dashboard
            val elapsedMs = measureTimeMillis {
                dashboard = coroutineScope {
                    val profile = async { repository.getProfile() }
                    val posts = async { repository.getPosts() }
                    val notifications = async { repository.getNotifications() }
                    Dashboard(
                        profile = profile.await(),
                        posts = posts.await(),
                        notifications = notifications.await()
                    )
                }
            }
            Log.d(
                TAG,
                "Parallel dashboard load: ${elapsedMs}ms (sequential would be ~2800ms)"
            )
            Resource.Success(dashboard)
        } catch (error: Exception) {
            if (error is CancellationException) throw error
            Resource.Error(error.message ?: "Could not load")
        }
    }

    private companion object {
        const val TAG = "TaskFlow"
    }
}
