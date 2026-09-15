package com.tp.taskflow.feature.auth.data

import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.core.network.TaskFlowApi
import com.tp.taskflow.core.network.TokenStore
import com.tp.taskflow.feature.auth.domain.AuthRepository
import com.tp.taskflow.feature.auth.domain.User
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: TaskFlowApi,
    private val tokenStore: TokenStore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<User> {
        return try {
            val response = api.login(LoginRequestDto(email, password))
            tokenStore.save(response.token)
            Resource.Success(response.user.toDomain())
        } catch (error: HttpException) {
            val message = when (error.code()) {
                401 -> "Invalid credentials"
                404 -> "Login endpoint not found"
                500 -> "Server error. Try again."
                else -> "Could not sign in (${error.code()})"
            }
            Resource.Error(message)
        } catch (error: Exception) {
            Resource.Error(error.message ?: "Could not sign in")
        }
    }

    override suspend fun logout() {
        tokenStore.clear()
    }
}
