package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localDataManager: LocalDataManager
) : ViewModel() {

    private val _existToken = MutableLiveData<Boolean>()
    val existToken : LiveData<Boolean>
        get() = _existToken

    fun getToken() {
        val token = localDataManager.getToken() ?: ""
        _existToken.value = token != ""
    }

}