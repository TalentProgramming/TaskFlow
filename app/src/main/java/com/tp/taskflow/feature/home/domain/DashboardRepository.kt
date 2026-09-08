package com.tp.taskflow.feature.home.domain

import com.tp.taskflow.feature.profile.domain.Profile
import com.tp.taskflow.feature.task.domain.TaskPost

interface DashboardRepository {
    suspend fun getProfile(): Profile
    suspend fun getPosts(): List<TaskPost>
    suspend fun getNotifications(): List<AppNotification>
}
