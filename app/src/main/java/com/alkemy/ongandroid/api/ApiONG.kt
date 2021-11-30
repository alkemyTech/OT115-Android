package com.alkemy.ongandroid.api

import com.alkemy.ongandroid.model.LoginData
import com.alkemy.ongandroid.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface ApiONG {

    @POST("login")
    fun login(
    @Body value: LoginData
    ): Call<ResponseLogin>
}