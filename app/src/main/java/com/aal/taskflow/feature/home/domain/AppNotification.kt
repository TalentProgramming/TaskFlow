package com.aal.taskflow.feature.home.domain

data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val unread: Boolean
)
