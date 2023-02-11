package com.ashu.savemytax.data

import com.google.gson.annotations.SerializedName

data class NotificationToken(
    @SerializedName("userId")
    val userId: String? = null,
    @SerializedName("token")
    val token: String? = null
)