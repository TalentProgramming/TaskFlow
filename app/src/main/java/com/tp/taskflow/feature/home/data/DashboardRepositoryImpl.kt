package com.tp.taskflow.feature.home.data

import com.tp.taskflow.core.network.TaskFlowApi
import com.tp.taskflow.feature.home.domain.AppNotification
import com.tp.taskflow.feature.home.domain.DashboardRepository
import com.tp.taskflow.feature.profile.data.toDomain
import com.tp.taskflow.feature.profile.domain.Profile
import com.tp.taskflow.feature.task.data.toDomain
import com.tp.taskflow.feature.task.domain.TaskPost
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val api: TaskFlowApi,
    private val notificationDataSource: FakeNotificationDataSource
) : DashboardRepository {

    override suspend fun getProfile(): Profile = api.profile().toDomain()

    override suspend fun getPosts(): List<TaskPost> = api.posts().map { it.toDomain() }

    override suspend fun getNotifications(): List<AppNotification> =
        notificationDataSource.getNotifications()
}
