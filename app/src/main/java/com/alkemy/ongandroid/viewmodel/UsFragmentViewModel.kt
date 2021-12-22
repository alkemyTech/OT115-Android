package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.*
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepoImpl
import com.alkemy.ongandroid.core.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class UsFragmentViewModel @Inject constructor(private val repo: ApiRepoImpl) : ViewModel() {

    private val _status = MutableLiveData(false)
    val status: LiveData<Boolean>
        get() = _status

    fun getMembers() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {

        _status.value = true
        try {
            emit(Response.Success(repo.getMembers()))
        } catch (e: Throwable) {
            emit(Response.Failure(e))
        } finally {
            _status.value = false
        }
    }

}