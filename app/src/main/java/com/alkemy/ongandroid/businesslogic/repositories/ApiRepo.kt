package com.alkemy.ongandroid.businesslogic.repositories

import com.alkemy.ongandroid.model.ApiNewsResponse

interface ApiRepo {

    suspend fun getNews(): ApiNewsResponse

}