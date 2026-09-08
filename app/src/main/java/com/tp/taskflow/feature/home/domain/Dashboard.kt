package com.tp.taskflow.feature.home.domain

import com.tp.taskflow.feature.profile.domain.Profile
import com.tp.taskflow.feature.task.domain.TaskPost

data class Dashboard(
    val profile: Profile,
    val posts: List<TaskPost>,
    val notifications: List<AppNotification>
)
