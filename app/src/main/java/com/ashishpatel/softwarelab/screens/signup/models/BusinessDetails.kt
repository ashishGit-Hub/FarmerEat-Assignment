package com.ashishpatel.softwarelab.screens.signup.models

import com.google.gson.annotations.SerializedName

data class BusinessDetails(
    @SerializedName("business_name")
    val businessName : String = "",
    @SerializedName("informal_name")
    val informalName : String = "",
    val address : String = "",
    val city : String = "",
    val  state : String = "",
    val zipCode : String = ""
)
