package com.ashu.ocotopus.api.user

import com.ashu.savemytax.data.NotificationToken
import com.ashu.savemytax.data.RegisterResponse
import com.ashu.savemytax.data.RegisterUser
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class UserApiHelperImp @Inject constructor(private val userApiService: UserApiService): UserAPiHelper {

    override suspend fun registerNewUser(registerUser: RegisterUser): Response<RegisterResponse> =
        userApiService.registerUser(registerUser)

    override suspend fun updateNotificationToken(notificationToken: NotificationToken):
            Response<Boolean> = userApiService.updateNotificationToken(notificationToken)

    override suspend fun sendNotificationToAll(): Response<Boolean> =
        userApiService.sendNotificationToAll()
//
//    override suspend fun getUserProfileData(userId: String?): Response<ProfileUser> =
//        userApiService.fetchUserProfile(userId)
//
//    override suspend fun updateUserProfileData(updateProfile: MultipartBody.Part?,
//                                               userPhoto: MultipartBody.Part?) =
//        userApiService.updateUserProfile(updateProfile, userPhoto)

}