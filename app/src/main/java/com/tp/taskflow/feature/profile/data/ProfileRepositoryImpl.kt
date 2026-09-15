package com.tp.taskflow.feature.profile.data

import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.core.network.TaskFlowApi
import com.tp.taskflow.feature.profile.domain.Profile
import com.tp.taskflow.feature.profile.domain.ProfileRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val api: TaskFlowApi
) : ProfileRepository {

    override suspend fun getProfile(): Resource<Profile> = wrap { api.profile().toDomain() }

    override suspend fun updateProfile(profile: Profile): Resource<Profile> = wrap {
        api.updateProfile(ProfileDto(profile.id, profile.name, profile.email)).toDomain()
    }

    override suspend fun uploadPhoto(file: File): Resource<Profile> = wrap {
        val part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            file.asRequestBody("image/*".toMediaType())
        )
        api.uploadPhoto(part).toDomain()
    }

    private suspend fun wrap(block: suspend () -> Profile): Resource<Profile> {
        return try {
            Resource.Success(block())
        } catch (error: HttpException) {
            val message = when (error.code()) {
                401 -> "Session expired"
                404 -> "Profile not found"
                500 -> "Server error"
                else -> "Could not load profile (${error.code()})"
            }
            Resource.Error(message)
        } catch (error: Exception) {
            Resource.Error(error.message ?: "Could not load profile")
        }
    }
}
