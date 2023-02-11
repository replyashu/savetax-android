package com.ashu.ocotopus.api.user

import com.ashu.savemytax.data.NotificationToken
import com.ashu.savemytax.data.RegisterResponse
import com.ashu.savemytax.data.RegisterUser
import com.google.firebase.firestore.auth.User
import okhttp3.MultipartBody
import retrofit2.Response

interface UserAPiHelper {

    suspend fun registerNewUser(registerUser: RegisterUser): Response<RegisterResponse>

    suspend fun updateNotificationToken(notificationToken: NotificationToken): Response<Boolean>

    suspend fun sendNotificationToAll(): Response<Boolean>

//    suspend fun getUserProfileData(userId: String?): Response<ProfileUser>
//
//    suspend fun updateUserProfileData(updateProfile: MultipartBody.Part?,
//                    userPhoto: MultipartBody.Part?): Response<ProfileUser>
}