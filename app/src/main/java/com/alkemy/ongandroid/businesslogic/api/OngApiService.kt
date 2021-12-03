package com.alkemy.ongandroid.businesslogic.api

import com.alkemy.ongandroid.model.ApiNewsResponse
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.UserRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OngApiService{
    @POST("register")
    suspend fun pushPost(
        @Body userRequest: UserRequest
    ) : NewUserResponse

    @GET("news")
    suspend fun getNews(): ApiNewsResponse
}