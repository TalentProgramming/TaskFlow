package com.tp.taskflow.feature.auth.domain

import com.tp.taskflow.core.common.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> =
        repository.login(email, password)
}
