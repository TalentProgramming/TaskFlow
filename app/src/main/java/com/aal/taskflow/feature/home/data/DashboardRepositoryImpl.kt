package com.aal.taskflow.feature.home.data

import com.aal.taskflow.feature.home.domain.AppNotification
import com.aal.taskflow.feature.home.domain.DashboardRepository
import com.aal.taskflow.feature.profile.data.FakeProfileDataSource
import com.aal.taskflow.feature.profile.domain.Profile
import com.aal.taskflow.feature.task.data.FakeTaskDataSource
import com.aal.taskflow.feature.task.domain.TaskPost

class DashboardRepositoryImpl(
    private val profileDataSource: FakeProfileDataSource = FakeProfileDataSource(),
    private val taskDataSource: FakeTaskDataSource = FakeTaskDataSource(),
    private val notificationDataSource: FakeNotificationDataSource = FakeNotificationDataSource()
) : DashboardRepository {

    override suspend fun getProfile(): Profile = profileDataSource.getProfile()

    override suspend fun getPosts(): List<TaskPost> = taskDataSource.getPosts()

    override suspend fun getNotifications(): List<AppNotification> =
        notificationDataSource.getNotifications()
}
