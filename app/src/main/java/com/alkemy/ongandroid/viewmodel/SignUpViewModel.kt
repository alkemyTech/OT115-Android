package com.alkemy.ongandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.User
import com.alkemy.ongandroid.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: UserRepository,
    private val localDataManager: LocalDataManager
) : ViewModel() {

    sealed class State {
        class Success(val response: NewUserResponse) : State()
        class Failure(val cause: Throwable) : State()
    }

    init {
        Log.e("TOKEN", localDataManager.getToken() ?: "")
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun addUserToRemoteDB(user: User) {
        viewModelScope.launch {
            val response = repository.addUserToRemoteDB(user)
            if (response.success) {
                _state.value = State.Success(response)
            }else{
                _state.value = State.Failure(Throwable(response.message))
            }
        }
    }
}