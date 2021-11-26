package com.alkemy.ongandroid.api

import com.alkemy.ongandroid.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface ApiONG {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>
}