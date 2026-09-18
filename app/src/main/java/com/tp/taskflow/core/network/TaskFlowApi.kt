package com.tp.taskflow.core.network

import com.tp.taskflow.feature.auth.data.LoginRequestDto
import com.tp.taskflow.feature.auth.data.TokenDto
import com.tp.taskflow.feature.product.data.ProductDto
import com.tp.taskflow.feature.profile.data.ProfileDto
import com.tp.taskflow.feature.task.data.PostDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface TaskFlowApi {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequestDto): TokenDto

    @GET("profile/me")
    suspend fun profile(): ProfileDto

    @GET("posts")
    suspend fun posts(): List<PostDto>

    @PUT("profile/me")
    suspend fun updateProfile(@Body body: ProfileDto): ProfileDto

    @Multipart
    @POST("profile/me/photo")
    suspend fun uploadPhoto(@Part photo: MultipartBody.Part): ProfileDto

    @GET("products")
    suspend fun products(@Query("q") query: String = "", @Query("page") page: Int = 1): List<ProductDto>
}
