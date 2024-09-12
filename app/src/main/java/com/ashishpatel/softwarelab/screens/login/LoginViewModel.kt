package com.ashishpatel.softwarelab.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashishpatel.softwarelab.data.repo.AuthRepo
import com.ashishpatel.softwarelab.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    val authLoginLiveData: LiveData<NetworkResult<String>> get() = authRepo.authLoginLiveData

    fun login(
        email: String,
        password: String,
        role: String,
        type: String,
        socialId: String
    ){
        viewModelScope.launch {
            authRepo.login(
                email,
                password,
                role,
                type,
                socialId
            )
        }
    }

    val forgotPasswordLiveData: LiveData<NetworkResult<String>> get() = authRepo.forgotPasswordLiveData

    fun forgotPassword(mobile : String){
        viewModelScope.launch {
            authRepo.forgotPassword(mobile)
        }
    }
    val verifyOTPLiveData: LiveData<NetworkResult<String>> get() = authRepo.verifyOTPLiveData

    fun verifyOTP(otp : String){
        viewModelScope.launch {
            authRepo.verifyOTP(otp)
        }
    }
    val resetPasswordLiveData: LiveData<NetworkResult<String>> get() = authRepo.resetPasswordLiveData

    fun resetPassword(body : Map<String,String>){
        viewModelScope.launch {
            authRepo.resetPassword(body)
        }
    }

}