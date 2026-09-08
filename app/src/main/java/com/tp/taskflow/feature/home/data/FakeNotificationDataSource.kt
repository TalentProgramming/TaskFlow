package com.tp.taskflow.feature.home.data

import com.tp.taskflow.feature.home.domain.AppNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeNotificationDataSource {
    suspend fun getNotifications(): List<AppNotification> = withContext(Dispatchers.IO) {
        delay(1100)
        listOf(
            AppNotification("n-1", "Lab reminder", "Screenshot Loading, Success, Error, and Retry.", unread = true),
            AppNotification("n-2", "New comment", "Your TaskFlow starter looks clean.", unread = true),
            AppNotification("n-3", "Course tip", "Do not swallow CancellationException.", unread = false)
        )
    }
}
