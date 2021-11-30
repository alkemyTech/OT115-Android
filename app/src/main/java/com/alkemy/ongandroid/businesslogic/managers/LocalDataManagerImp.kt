package com.alkemy.ongandroid.businesslogic.managers

import android.content.Context

class LocalDataManagerImp(
    private val ctx: Context
): LocalDataManager {

    private val sharedPreferences = ctx.getSharedPreferences(SP,Context.MODE_PRIVATE)

    companion object{
        private const val SP = "sharedPref"
        private const val USER_TOKEN_KEY = "UserToken"
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().apply{
            putString(USER_TOKEN_KEY,token)
        }
    }

    override fun getToken() = sharedPreferences.getString(USER_TOKEN_KEY,"")


}