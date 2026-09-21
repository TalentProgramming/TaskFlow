package com.tp.taskflow.feature.auth.domain

import com.tp.taskflow.core.common.Resource
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        phone: String
    ): Resource<User> = repository.register(name, email, password, phone)
}