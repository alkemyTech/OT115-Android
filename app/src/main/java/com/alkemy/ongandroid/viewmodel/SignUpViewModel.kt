package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.UserRequest
import com.alkemy.ongandroid.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    sealed class State {
        class Success(val response: NewUserResponse) : State()
        class Failure(val cause: Throwable) : State()
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun addUserToRemoteDB(userRequest: UserRequest) {
        viewModelScope.launch {
            val response = repository.addUserToRemoteDB(userRequest)
            if (response.success) {
                _state.value = State.Success(response)
            }
        }
    }
}