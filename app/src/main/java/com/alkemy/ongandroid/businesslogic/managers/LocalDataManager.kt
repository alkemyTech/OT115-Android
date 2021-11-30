package com.alkemy.ongandroid.businesslogic.managers

interface LocalDataManager {

    fun saveToken(token: String)

    fun getToken():String?

}