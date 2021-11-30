package com.alkemy.ongandroid.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.api.ApiONGImp
import com.alkemy.ongandroid.model.ResponseLogin
import com.alkemy.ongandroid.view.LoginActivity
import com.alkemy.ongandroid.view.MainActivity
import retrofit2.Call
import retrofit2.awaitResponse

class LoginViewModel: ViewModel() {

    private var loginfo = mutableListOf<ResponseLogin>()

    private fun getLogin(email: String, pass: String): Call<ResponseLogin>{
        return ApiONGImp().login(email,pass)
    }

    suspend fun login(email: String, pass:String ): MutableList<ResponseLogin> {

        val resp = getLogin(email,pass).awaitResponse()

        if(resp.isSuccessful){
            val info = resp.body()
            if(info != null){
                loginfo.add(info)

            }
        }
        return loginfo
    }


}

