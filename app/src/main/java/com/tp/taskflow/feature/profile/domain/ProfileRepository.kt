package com.tp.taskflow.feature.profile.domain

import com.tp.taskflow.core.common.Resource
import java.io.File

interface ProfileRepository {
    suspend fun getProfile(): Resource<Profile>
    suspend fun updateProfile(profile: Profile): Resource<Profile>
    suspend fun uploadPhoto(file: File): Resource<Profile>
}
