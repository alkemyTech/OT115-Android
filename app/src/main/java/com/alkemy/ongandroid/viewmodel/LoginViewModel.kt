package com.alkemy.ongandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.api.ApiONGImp
import com.alkemy.ongandroid.model.ResponseLogin
import retrofit2.Call
import retrofit2.awaitResponse

class LoginViewModel: ViewModel() {

    private var loginfo = ArrayList<ResponseLogin>()

    private fun getLogin(email: String, password: String): Call<ResponseLogin>{
        return ApiONGImp().Login(email,password)
    }

    suspend fun login(email: String, password:String ): ArrayList<ResponseLogin> {

        //val resp = ApiONGImp().Login(email,password).awaitResponse()
        val resp = getLogin(email,password).awaitResponse()

        if(resp.isSuccessful){
            Log.e("Hasta k: ", "master Master master")
            val info = resp.body()
            if(info != null){
                Log.e("Data: ", info.success)
                loginfo.add(info)
            }
        }
        return loginfo
    }
}

