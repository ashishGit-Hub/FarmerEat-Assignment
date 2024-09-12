package com.ashishpatel.softwarelab.screens.signup

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashishpatel.softwarelab.data.repo.AuthRepo
import com.ashishpatel.softwarelab.screens.signup.models.BusinessDetails
import com.ashishpatel.softwarelab.screens.signup.models.BusinessHour
import com.ashishpatel.softwarelab.screens.signup.models.BusinessHoursDetails
import com.ashishpatel.softwarelab.screens.signup.models.UserDetails
import com.ashishpatel.softwarelab.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    var userDetails: UserDetails = UserDetails()
        private set

    var businessDetails: BusinessDetails = BusinessDetails()
        private set

    var documentUri : Uri?  = null

    var businessHours : BusinessHoursDetails = BusinessHoursDetails()
        private set
    fun setHours(hoursList : List<BusinessHour>){
        when(hoursList.first().day){
            "monday"->{
                val l = hoursList.filter { it.selected }
                businessHours = businessHours.copy(mon = l.map { it.hour })
            }
            "tuesday"->{
                val l = hoursList.filter { it.selected }
                businessHours = businessHours.copy(tue = l.map { it.hour })
            }
            "wednesday"->{
                val l = hoursList.filter { it.selected }
                businessHours = businessHours.copy(wed = l.map { it.hour })
            }
            "thursday"->{
                val l = hoursList.filter { it.selected }
                businessHours = businessHours.copy(thu = l.map { it.hour })
            }
            "friday"->{
                val l = hoursList.filter { it.selected }
                businessHours = businessHours.copy(fri = l.map { it.hour })
            }
            "saturday"->{
                val l = hoursList.filter { it.selected }
                businessHours = businessHours.copy(sat = l.map { it.hour })
            }
            "sunday"->{
                val l = hoursList.filter { it.selected }
                businessHours = businessHours.copy(sun = l.map { it.hour })
            }
        }
    }

    val authRegisterLiveData: LiveData<NetworkResult<String>> get() = authRepo.authRegisterLiveData
    fun setUserDetails(userDetails: UserDetails) {
        this.userDetails = userDetails
    }

    fun setBusinessDetails(businessDetails: BusinessDetails) {
        this.businessDetails = businessDetails
    }
    fun setBusinessDetails(businessHours: BusinessHoursDetails) {
        this.businessHours = businessHours
    }

    fun register(){
        viewModelScope.launch {
            authRepo.register(
                userDetails = userDetails,
                businessDetails =  businessDetails,
                businessHour =  businessHours,
                documentUri!!
            )
        }
    }

}