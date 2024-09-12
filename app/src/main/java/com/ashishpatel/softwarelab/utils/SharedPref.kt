package com.ashishpatel.softwarelab.utils

import android.content.Context
import com.ashishpatel.softwarelab.utils.Constants.TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(Constants.AUTH_STORAGE, Context.MODE_PRIVATE)

    fun saveToken(token : String){
        prefs.edit().putString(TOKEN,token).apply()
    }

    fun getToken() : String? = prefs.getString(TOKEN,null)
    fun removeToken(){
        prefs.edit().clear().apply()
    }

}