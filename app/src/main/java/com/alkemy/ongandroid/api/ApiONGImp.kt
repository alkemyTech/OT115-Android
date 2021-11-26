package com.alkemy.ongandroid.api

import com.alkemy.ongandroid.model.ResponseLogin
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiONGImp {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://ongapi.alkemy.org/api/").build()
    }

    fun Login(email:String, password:String): Call<ResponseLogin> {
        val resp = getRetrofit().create(ApiONG::class.java).login(email,password)

        return resp
    }
}