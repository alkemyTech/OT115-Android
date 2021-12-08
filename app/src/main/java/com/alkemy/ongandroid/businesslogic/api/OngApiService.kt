package com.alkemy.ongandroid.businesslogic.api

import com.alkemy.ongandroid.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OngApiService{
    @POST("register")
    suspend fun pushPost(
        @Body userRequest: UserRequest
    ) : NewUserResponse

    @POST("login")
    suspend fun login(
        @Body value: LoginData
    ): NewUserResponse

    @GET("news")
    suspend fun getNews(): ApiNewsResponse

    @GET("testimonials")
    suspend fun getTestimonials(): ApiTestimonialsResponse
}