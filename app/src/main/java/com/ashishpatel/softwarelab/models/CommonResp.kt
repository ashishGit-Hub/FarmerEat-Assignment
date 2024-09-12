package com.ashishpatel.softwarelab.models

import com.google.gson.annotations.SerializedName

data class CommonResp(
    val message: String = "",
    val success: String = "",
    val token : String = ""
)