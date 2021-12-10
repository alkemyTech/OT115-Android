package com.alkemy.ongandroid.businesslogic.repositories

import com.alkemy.ongandroid.model.ApiNewsResponse
import com.alkemy.ongandroid.model.ApiSlidesResponse
import com.alkemy.ongandroid.model.ApiTestimonialsResponse

interface ApiRepo {

    suspend fun getNews(): ApiNewsResponse
    suspend fun getTestimonials(): ApiTestimonialsResponse
    suspend fun getSlides(): ApiSlidesResponse
}