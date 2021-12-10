package com.alkemy.ongandroid.businesslogic.repositories

import com.alkemy.ongandroid.businesslogic.api.OngApiService
import com.alkemy.ongandroid.model.ApiNewsResponse
import com.alkemy.ongandroid.model.ApiSlidesResponse
import com.alkemy.ongandroid.model.ApiTestimonialsResponse
import javax.inject.Inject

class ApiRepoImpl @Inject constructor (private val apiService : OngApiService) : ApiRepo {

    override suspend fun getNews(): ApiNewsResponse = apiService.getNews()
    override suspend fun getTestimonials(): ApiTestimonialsResponse = apiService.getTestimonials()
    override suspend fun getSlides(): ApiSlidesResponse = apiService.getSlides()
}