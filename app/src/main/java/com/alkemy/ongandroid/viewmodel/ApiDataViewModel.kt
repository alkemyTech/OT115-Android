package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.*
import com.alkemy.ongandroid.core.Response
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ApiDataViewModel @Inject constructor (private val repo: ApiRepoImpl): ViewModel() {


    fun getNews() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {

        emit(Response.Loading())
        try {
            emit(Response.Success(repo.getNews()))
        }catch (e:Throwable){
            emit(Response.Failure(e))
        }
    }
}