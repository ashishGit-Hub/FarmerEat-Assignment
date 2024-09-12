package com.ashishpatel.softwarelab.models

import com.google.gson.annotations.SerializedName

data class AccountPreference(
    val currency: String,
    val locale: String,
    @SerializedName("time_zone")
    val timeZone: String
)