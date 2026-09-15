package com.tp.taskflow.feature.auth.data

import com.tp.taskflow.feature.auth.domain.User

data class LoginRequestDto(
    val email: String,
    val password: String
)

data class TokenDto(
    val token: String = "",
    val user: UserDto = UserDto()
)

data class UserDto(
    val id: String = "",
    val name: String = "",
    val email: String = ""
)

fun UserDto.toDomain(): User = User(id, name, email)
