package com.alkemy.ongandroid.model.apidatarepo

import com.alkemy.ongandroid.businesslogic.api.OngApiService
import com.alkemy.ongandroid.model.ApiNewsResponse
import javax.inject.Inject

class ApiRepoImpl @Inject constructor (private val apiService : OngApiService) : ApiRepo {

    override suspend fun getNews(): ApiNewsResponse = apiService.getNews()
}