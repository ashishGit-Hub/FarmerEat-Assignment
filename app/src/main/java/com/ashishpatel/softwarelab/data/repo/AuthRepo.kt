package com.ashishpatel.softwarelab.data.repo

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ashishpatel.softwarelab.data.apis.AuthAPI
import com.ashishpatel.softwarelab.screens.signup.models.BusinessDetails
import com.ashishpatel.softwarelab.screens.signup.models.BusinessHoursDetails
import com.ashishpatel.softwarelab.screens.signup.models.UserDetails
import com.ashishpatel.softwarelab.utils.Constants.INTERNAL_ERROR
import com.ashishpatel.softwarelab.utils.Constants.MESSAGE
import com.ashishpatel.softwarelab.utils.Constants.NO_INTERNET_CONNECTION
import com.ashishpatel.softwarelab.utils.Constants.SERVER_ERROR
import com.ashishpatel.softwarelab.utils.NetworkManager
import com.ashishpatel.softwarelab.utils.NetworkResult
import com.ashishpatel.softwarelab.utils.SharedPref
import com.google.gson.Gson
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject


class AuthRepo @Inject constructor(
    private val authAPI: AuthAPI,
    private val networkManager: NetworkManager,
    private val sharedPref: SharedPref
) {

    private val _authLoginLiveData = MutableLiveData<NetworkResult<String>>()
    val authLoginLiveData: LiveData<NetworkResult<String>> get() = _authLoginLiveData

    suspend fun login(
        email: String,
        password: String,
        role: String,
        type: String,
        socialId: String
    ) {

        _authLoginLiveData.postValue(NetworkResult.Loading())

        if (!networkManager.internetConnected) {
            _authLoginLiveData.postValue(NetworkResult.Error(NO_INTERNET_CONNECTION))
        }

        val body = mapOf(
            "email" to email,
            "password" to password,
            "role" to role,
            "type" to type,
            "social_id" to socialId
        )
        try {
            val response = authAPI.login(body)

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.success.toBoolean()) {
                        _authLoginLiveData.postValue(NetworkResult.Success(responseBody.message))
                        sharedPref.saveToken(responseBody.token)
                    } else {
                        _authLoginLiveData.postValue(NetworkResult.Error(responseBody.message))
                    }
                } else {
                    _authLoginLiveData.postValue(NetworkResult.Error(SERVER_ERROR))
                }

            } else {
                val errorBody = JSONObject(response.errorBody()?.charStream()!!.readText())
                _authLoginLiveData.postValue(NetworkResult.Error(errorBody.getString(MESSAGE)))
            }
        } catch (e: IOException) {
            _authLoginLiveData.postValue(NetworkResult.Error(INTERNAL_ERROR))
        }
    }

    private val _authRegisterLiveData = MutableLiveData<NetworkResult<String>>()
    val authRegisterLiveData: LiveData<NetworkResult<String>> get() = _authRegisterLiveData

    suspend fun register(
        userDetails: UserDetails,
        businessDetails: BusinessDetails,
        businessHour: BusinessHoursDetails,
        uri: Uri
    ) {

        _authRegisterLiveData.postValue(NetworkResult.Loading())

        if (!networkManager.internetConnected) {
            _authRegisterLiveData.postValue(NetworkResult.Error(NO_INTERNET_CONNECTION))
        }

        try {
            val response = authAPI.register(mapOf(
                "full_name" to userDetails.fullName,
                "email" to userDetails.email,
                "phone" to userDetails.phone,
                "password" to userDetails.password,
                "role" to "farmer",
                "business_name" to businessDetails.businessName,
                "informal_name" to businessDetails.informalName,
                "address" to businessDetails.address,
                "city" to businessDetails.city,
                "state" to businessDetails.state,
                "zip_code" to businessDetails.zipCode,
                "registration_proof" to uri.toString(),
                "business_hours" to Gson().toJson(businessHour),
                "device_token" to "0imfnc8mVLWwsAawjYr4Rx-Af50DDqtlx",
                "type" to "email",
                "social_id" to "0imfnc8mVLWwsAawjYr4Rx-Af50DDqtlx"
            ) )

            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("Registration", responseBody.toString())
                if (responseBody != null) {
                    if (responseBody.success.toBoolean()) {
                        _authRegisterLiveData.postValue(NetworkResult.Success(responseBody.message))
                        sharedPref.saveToken(responseBody.token)
                    } else {
                        _authRegisterLiveData.postValue(NetworkResult.Error(responseBody.message))
                    }
                } else {
                    _authRegisterLiveData.postValue(NetworkResult.Error(SERVER_ERROR))
                }
            } else {
                val errorBody = JSONObject(response.errorBody()?.charStream()!!.readText())
                _authRegisterLiveData.postValue(NetworkResult.Error(errorBody.getString(MESSAGE)))
            }

        } catch (e: IOException) {
            _authRegisterLiveData.postValue(NetworkResult.Error(INTERNAL_ERROR))
        }
    }

    private val _forgotPasswordLiveData = MutableLiveData<NetworkResult<String>>()
    val forgotPasswordLiveData: LiveData<NetworkResult<String>> get() = _forgotPasswordLiveData

    suspend fun forgotPassword(mobile : String){
        _forgotPasswordLiveData.postValue(NetworkResult.Loading())

        if (!networkManager.internetConnected) {
            _forgotPasswordLiveData.postValue(NetworkResult.Error(NO_INTERNET_CONNECTION))
        }

        val body = mapOf("mobile" to mobile )

        try {
            val response = authAPI.forgotPassword(body)

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.success.toBoolean()) {
                        _forgotPasswordLiveData.postValue(NetworkResult.Success(responseBody.message))
                    } else {
                        _forgotPasswordLiveData.postValue(NetworkResult.Error(responseBody.message))
                    }
                } else {
                    _forgotPasswordLiveData.postValue(NetworkResult.Error(SERVER_ERROR))
                }

            } else {
                val errorBody = JSONObject(response.errorBody()?.charStream()!!.readText())
                _forgotPasswordLiveData.postValue(NetworkResult.Error(errorBody.getString(MESSAGE)))
            }

        } catch (e: IOException) {
            _forgotPasswordLiveData.postValue(NetworkResult.Error(INTERNAL_ERROR))
        }
    }

    private val _verifyOTPLiveData = MutableLiveData<NetworkResult<String>>()
    val verifyOTPLiveData: LiveData<NetworkResult<String>> get() = _verifyOTPLiveData

    suspend fun verifyOTP(otp : String){
        _verifyOTPLiveData.postValue(NetworkResult.Loading())

        if (!networkManager.internetConnected) {
            _verifyOTPLiveData.postValue(NetworkResult.Error(NO_INTERNET_CONNECTION))
        }

        val body = mapOf("otp" to otp )

        try {
            val response = authAPI.verifyOTP(body)

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.success.toBoolean()) {
                        _verifyOTPLiveData.postValue(NetworkResult.Success(responseBody.token))
                    } else {
                        _verifyOTPLiveData.postValue(NetworkResult.Error(responseBody.message))
                    }
                } else {
                    _verifyOTPLiveData.postValue(NetworkResult.Error(SERVER_ERROR))
                }

            } else {
                val errorBody = JSONObject(response.errorBody()?.charStream()!!.readText())
                _verifyOTPLiveData.postValue(NetworkResult.Error(errorBody.getString(MESSAGE)))
            }

        } catch (e: IOException) {
            _verifyOTPLiveData.postValue(NetworkResult.Error(INTERNAL_ERROR))
        }
    }
    private val _resetPasswordLiveData = MutableLiveData<NetworkResult<String>>()
    val resetPasswordLiveData: LiveData<NetworkResult<String>> get() = _resetPasswordLiveData

    suspend fun resetPassword(body : Map<String,String>){
        _resetPasswordLiveData.postValue(NetworkResult.Loading())

        if (!networkManager.internetConnected) {
            _resetPasswordLiveData.postValue(NetworkResult.Error(NO_INTERNET_CONNECTION))
        }

        try {
            val response = authAPI.resetPassword(body)

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.success.toBoolean()) {
                        _resetPasswordLiveData.postValue(NetworkResult.Success(responseBody.token))
                    } else {
                        _resetPasswordLiveData.postValue(NetworkResult.Error(responseBody.message))
                    }
                } else {
                    _resetPasswordLiveData.postValue(NetworkResult.Error(SERVER_ERROR))
                }

            } else {
                val errorBody = JSONObject(response.errorBody()?.charStream()!!.readText())
                _resetPasswordLiveData.postValue(NetworkResult.Error(errorBody.getString(MESSAGE)))
            }

        } catch (e: IOException) {
            _resetPasswordLiveData.postValue(NetworkResult.Error(INTERNAL_ERROR))
        }
    }

}