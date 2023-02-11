package com.ashu.savemytax

import com.ashu.ocotopus.api.user.UserAPiHelper
import com.ashu.savemytax.data.NotificationToken
import com.ashu.savemytax.data.RegisterUser
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPiHelper: UserAPiHelper) {

    suspend fun registerUser(registerUser: RegisterUser) = userAPiHelper.registerNewUser(registerUser)

    suspend fun updateNotificationToken(notificationToken: NotificationToken) =
        userAPiHelper.updateNotificationToken(notificationToken)

    suspend fun sendNotifications() = userAPiHelper.sendNotificationToAll()

//    suspend fun fetchUserData(userId: String?) = userAPiHelper.getUserProfileData(userId)
//
//    suspend fun updateUserData(updateProfile: MultipartBody.Part?,
//            userPhoto: MultipartBody.Part?) =
//        userAPiHelper.updateUserProfileData(updateProfile, userPhoto)

}