package com.tp.taskflow.feature.auth.domain

import com.tp.taskflow.core.common.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<User>
    suspend fun register(name: String, email: String, password: String, phone: String): Resource<User>
    suspend fun logout()
}
