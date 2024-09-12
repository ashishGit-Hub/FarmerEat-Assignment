package com.ashishpatel.softwarelab.data.apis

import com.ashishpatel.softwarelab.models.CommonResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("user/register")
    suspend fun register(
        @Body map: Map<String, String>
    ): Response<CommonResp>

    @POST("user/login")
    suspend fun login(
        @Body body: Map<String, String>
    ): Response<CommonResp>

    @POST("user/forgot-password")
    suspend fun forgotPassword(
        @Body body: Map<String,String>
    ) : Response<CommonResp>

    @POST("user/verify-otp")
    suspend fun verifyOTP(
        @Body body: Map<String,String>
    ) : Response<CommonResp>

    @POST("user/reset-password")
    suspend fun resetPassword(
        @Body body: Map<String,String>
    ) : Response<CommonResp>

}