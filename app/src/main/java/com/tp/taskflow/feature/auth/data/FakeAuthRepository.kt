package com.tp.taskflow.feature.auth.data

import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.feature.auth.domain.User

import javax.inject.Inject

class FakeAuthRepository @Inject constructor() {

    fun login(email: String, password: String): Resource<User> {
        return if (email == DEMO_EMAIL && password == DEMO_PASSWORD) {
            Resource.Success(
                User(
                    id = "u-1",
                    name = "Aung Ko",
                    email = email
                ).also { user ->
                    rememberSignedIn(user.name, user.email)
                }
            )
        } else {
            Resource.Error("Invalid credentials")
        }
    }

    companion object {
        const val DEMO_EMAIL = "student@example.com"
        const val DEMO_PASSWORD = "123456"

        var lastSignedInName: String = "Aung Ko"
            private set
        var lastSignedInEmail: String? = null
            private set

        fun rememberSignedIn(name: String, email: String) {
            lastSignedInName = name
            lastSignedInEmail = email
        }
    }
}
