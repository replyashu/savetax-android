package com.ashu.savemytax.api.user

import com.ashu.savemytax.data.NotificationToken
import com.ashu.savemytax.data.RegisterResponse
import com.ashu.savemytax.data.RegisterUser
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @POST("user/save")
    suspend fun registerUser(@Body registerUser: RegisterUser): Response<RegisterResponse>

    @POST("user/enable-push")
    suspend fun updateNotificationToken(@Body notificationToken: NotificationToken): Response<Boolean>

    @POST("user/notify")
    suspend fun sendNotificationToAll(): Response<Boolean>

//    @GET("user/profile")
//    suspend fun fetchUserProfile(@Query("userId") userId: String?): Response<ProfileUser>
//
//    @Multipart
//    @POST("user/edit-profile")
//    suspend fun updateUserProfile(@Part updateProfile: MultipartBody.Part?,
//                    @Part userPhoto: MultipartBody.Part?): Response<ProfileUser>
}