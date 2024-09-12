package com.ashishpatel.softwarelab.screens.signup.models

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("full_name")
    val fullName : String = "",
    val email : String = "",
    val phone : String = "",
    val password : String = "",
//    @SerializedName("device_token")
//    val deviceToken : String = "",
//    val type : String = "",
//    @SerializedName("social_id")
//    val socialId : String = "",

)