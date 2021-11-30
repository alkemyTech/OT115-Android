package com.alkemy.ongandroid.businesslogic.managers

import android.content.Context
import android.util.Log

class LocalDataManagerImp(
    ctx: Context
): LocalDataManager {

    private val sharedPreferences = ctx.getSharedPreferences(SP,Context.MODE_PRIVATE)

    companion object{
        private const val SP = "sharedPref"
        private const val USER_TOKEN_KEY = "UserToken"
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().apply {
            putString(USER_TOKEN_KEY, token)
        }.apply()
        Log.e("MANAGER, TOKEN: ", token)
    }

    override fun getToken(): String? {
        val token = sharedPreferences.getString(USER_TOKEN_KEY, "")
        Log.e("MANAGER, TOKEN: ", token ?: "")
        return token
    }
}