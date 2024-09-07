package com.ashishpatel.softwarelab.data.repo

import com.ashishpatel.softwarelab.data.apis.AuthAPI
import com.ashishpatel.softwarelab.utils.NetworkManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class AuthRepo @Inject constructor(
    private val authAPI: AuthAPI,
    private val networkManager: NetworkManager
) {

    suspend fun login(
        email: String,
        password: String,
        role: String,
        deviceToken: String,
        type: String,
        socialId: String,
    ) {
        val emailRequestBody = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val passwordRequestBody = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val roleRequestBody = role.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val deviceTokenRequestBody =
            deviceToken.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val typeRequestBody = type.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val socialIdRequestBody = socialId.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        authAPI.login(
            email = emailRequestBody,
            password = passwordRequestBody,
            role = roleRequestBody,
            deviceToken = deviceTokenRequestBody,
            type = typeRequestBody,
            socialId = socialIdRequestBody
        )
    }

}