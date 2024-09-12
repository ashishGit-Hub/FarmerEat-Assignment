package com.ashishpatel.softwarelab.utils

import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

object Extension {

    fun Fragment.toast(msg : String?){
        if(msg != null){
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun View.gone(){
        visibility = View.GONE
    }
    fun View.visible(){
        visibility = View.VISIBLE
    }


    fun TextInputLayout.getText(msg : String) : String?{
        val value = editText?.text?.trim().toString()
        return if(value.isEmpty()){
            isErrorEnabled = true
            error = msg
            null
        }else{
            isErrorEnabled = false
            error = null
            value
        }
    }

    fun TextInputLayout.getEmail(): String?{
        val value = editText?.text?.trim().toString()
        return if(value.isEmpty()){
            isErrorEnabled = true
            error = "Email cannot be empty."
            null
        }else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            isErrorEnabled = true
            error = "Email Invalid."
            null
        }else{
            isErrorEnabled = false
            error = null
            value
        }
    }


}