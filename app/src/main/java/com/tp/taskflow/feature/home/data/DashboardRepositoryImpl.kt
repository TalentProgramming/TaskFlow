package com.tp.taskflow.feature.home.data

import com.tp.taskflow.feature.home.domain.AppNotification
import com.tp.taskflow.feature.home.domain.DashboardRepository
import com.tp.taskflow.feature.profile.data.FakeProfileDataSource
import com.tp.taskflow.feature.profile.domain.Profile
import com.tp.taskflow.feature.task.data.FakeTaskDataSource
import com.tp.taskflow.feature.task.domain.TaskPost

import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val profileDataSource: FakeProfileDataSource,
    private val taskDataSource: FakeTaskDataSource,
    private val notificationDataSource: FakeNotificationDataSource
) : DashboardRepository {

    override suspend fun getProfile(): Profile = profileDataSource.getProfile()

    override suspend fun getPosts(): List<TaskPost> = taskDataSource.getPosts()

    override suspend fun getNotifications(): List<AppNotification> =
        notificationDataSource.getNotifications()
}
