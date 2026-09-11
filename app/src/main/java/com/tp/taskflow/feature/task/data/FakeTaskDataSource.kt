package com.tp.taskflow.feature.task.data

import com.tp.taskflow.feature.task.domain.TaskPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeTaskDataSource @Inject constructor() : TaskDataSource {
    private var failNext = true

    suspend fun getPosts(): List<TaskPost> = withContext(Dispatchers.IO) {
        delay(900)
        if (failNext) {
            failNext = false
            error("Posts service unavailable")
        }
        listOf(
            TaskPost("t-1", "Prepare Chapter 3 lab", "Build the parallel dashboard loader."),
            TaskPost("t-2", "Review Resource states", "Loading, Success, Error, Empty."),
            TaskPost("t-3", "Practice async/await", "Join profile, posts, and notifications.")
        )
    }
}
