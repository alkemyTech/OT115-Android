package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepo
import com.alkemy.ongandroid.core.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ApiDataViewModel @Inject constructor(private val repo: ApiRepo) : ViewModel() {


    fun getNews() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {

        emit(Response.Loading())
        try {
            emit(Response.Success(repo.getNews()))
        } catch (e: Throwable) {
            emit(Response.Failure(e))
        }
    }
}