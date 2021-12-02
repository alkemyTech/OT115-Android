package com.alkemy.ongandroid.model.apidatarepo

import com.alkemy.ongandroid.model.ApiNewsResponse

interface ApiRepo {

    suspend fun getNews(): ApiNewsResponse

}