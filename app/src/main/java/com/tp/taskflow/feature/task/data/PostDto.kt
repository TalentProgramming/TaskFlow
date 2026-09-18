package com.tp.taskflow.feature.task.data

import com.tp.taskflow.feature.task.domain.TaskPost

data class PostDto(
    val id: String = "",
    val title: String = "",
    val note: String = ""
)

fun PostDto.toDomain(): TaskPost = TaskPost(id, title, note)
