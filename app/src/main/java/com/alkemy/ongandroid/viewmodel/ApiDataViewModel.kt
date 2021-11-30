package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.*
import com.alkemy.ongandroid.core.Response
import com.alkemy.ongandroid.model.ApiData
import com.alkemy.ongandroid.model.ApiNewsResponse
import com.alkemy.ongandroid.model.apidatarepo.ApiRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ApiDataViewModel @Inject constructor (private val repo: ApiRepoImpl): ViewModel() {


    fun fetchNews() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {

        emit(Response.Loading())
        try {
            emit(Response.Success(repo.getNews()))
        }catch (e:Throwable){
            emit(Response.Failure(e))
        }
    }

    private val _isEmptyList = MutableLiveData(false)
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun checkListOfNews(list: List<ApiData>){
        _isEmptyList.value = list.isEmpty()
    }
}