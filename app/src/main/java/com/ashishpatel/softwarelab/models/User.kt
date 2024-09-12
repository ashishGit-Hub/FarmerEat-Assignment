package com.ashishpatel.softwarelab.models

import com.google.gson.annotations.SerializedName

data class User(
    val avatar: String,
    @SerializedName("device_token")
    val deviceToken: String,
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    val id: String,
    @SerializedName("social_id")
    val socialId: String,
    val type: String
)