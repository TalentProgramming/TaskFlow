package com.aal.taskflow.feature.home.domain

import com.aal.taskflow.feature.profile.domain.Profile
import com.aal.taskflow.feature.task.domain.TaskPost

interface DashboardRepository {
    suspend fun getProfile(): Profile
    suspend fun getPosts(): List<TaskPost>
    suspend fun getNotifications(): List<AppNotification>
}
