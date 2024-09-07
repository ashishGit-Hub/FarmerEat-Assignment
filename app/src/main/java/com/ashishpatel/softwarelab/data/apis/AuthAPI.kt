package com.ashishpatel.softwarelab.data.apis

import okhttp3.RequestBody
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthAPI {

    @POST("user/register")
    @Headers("Content-Type:multipart/form-data")
    suspend fun login(
        @Part("email") email : RequestBody,
        @Part("password") password : RequestBody,
        @Part("role") role : RequestBody,
        @Part("device_token") deviceToken : RequestBody,
        @Part("type") type : RequestBody,
        @Part("social_id") socialId : RequestBody,
    )

}