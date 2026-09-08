package com.aal.taskflow.feature.home.domain

import com.aal.taskflow.feature.profile.domain.Profile
import com.aal.taskflow.feature.task.domain.TaskPost

data class Dashboard(
    val profile: Profile,
    val posts: List<TaskPost>,
    val notifications: List<AppNotification>
)
