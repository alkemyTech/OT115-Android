package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepoImpl
import com.alkemy.ongandroid.core.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class UsFragmentViewModel @Inject constructor(private val repo: ApiRepoImpl) : ViewModel() {


    fun getMembers() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {

        emit(Response.Loading())
        try {
            emit(Response.Success(repo.getMembers()))
        }catch (e:Throwable){
            emit(Response.Failure(e))
        }
    }
}