package com.ashu.savemytax.data

import com.google.gson.annotations.SerializedName

data class RegisterUser(
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("email")
    val email: String? = null
)