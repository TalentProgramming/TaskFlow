package com.tp.taskflow.feature.auth.data

import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.feature.auth.domain.User

class FakeAuthRepository {

    fun login(email: String, password: String): Resource<User> {
        return if (email == DEMO_EMAIL && password == DEMO_PASSWORD) {
            Resource.Success(
                User(
                    id = "u-1",
                    name = "Aung Ko",
                    email = email
                ).also { user ->
                    lastSignedInEmail = user.email
                }
            )
        } else {
            Resource.Error("Invalid credentials")
        }
    }

    companion object {
        const val DEMO_EMAIL = "student@example.com"
        const val DEMO_PASSWORD = "123456"

        var lastSignedInEmail: String? = null
            private set
    }
}
