package com.ashishpatel.softwarelab.screens.signup.models

import com.google.gson.annotations.SerializedName

data class BusinessHoursDetails(
    val mon : List<String> = listOf(),
    val tue : List<String> = listOf(),
    val wed : List<String> = listOf(),
    val thu : List<String> = listOf(),
    val fri : List<String> = listOf(),
    val sat : List<String> = listOf(),
    val sun : List<String> = listOf()
)
