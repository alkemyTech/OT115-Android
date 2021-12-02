package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localDataManager: LocalDataManager
) : ViewModel() {

    fun getToken() : String {
        val token = localDataManager.getToken() ?: ""
        return token
    }

}