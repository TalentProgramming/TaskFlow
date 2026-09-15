package com.tp.taskflow.feature.profile.data

import com.tp.taskflow.feature.profile.domain.Profile

data class ProfileDto(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String? = null
)

fun ProfileDto.toDomain(): Profile = Profile(id, name, email)
