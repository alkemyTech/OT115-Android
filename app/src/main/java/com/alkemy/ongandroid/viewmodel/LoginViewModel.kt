package com.alkemy.ongandroid.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.businesslogic.PASSWORD_REGEX_WO_EC
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.businesslogic.repositories.UserRepository
import com.alkemy.ongandroid.model.LoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    sealed class State {
        object Success : State()
        object Failure : State()
        object NetworkError : State()
        object ApiError : State()
        object BadRequest : State()
        object GenericError : State()
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private val _viewState = MutableLiveData(false)
    val viewState: LiveData<Boolean>
        get() = _viewState

    private val _progressBarStatus = MutableLiveData(false)
    val progressBarStatus
        get() = _progressBarStatus

    fun login(email: String, pass: String) {
        _progressBarStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val resp = repository.logUser(LoginData(email, pass))
            handleLoginResponse(resp)
        }
    }

    fun validateFields(email: String, password: String) {

        val fieldsEmpty: Boolean = email == "" || password == ""
        val emailFormat: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordsFormat: Boolean = Pattern.matches(PASSWORD_REGEX_WO_EC, password)

        _viewState.value = !fieldsEmpty && emailFormat && passwordsFormat
    }

    private suspend fun handleLoginResponse(response: UserRepository.LoginResult) = when(response){
        is UserRepository.LoginResult.Success -> withContext(Dispatchers.Main) {
            _state.value = State.Success
            _progressBarStatus.value = false
        }
        is UserRepository.LoginResult.NoToken -> withContext(Dispatchers.Main) {
            _state.value = State.Failure
            _progressBarStatus.value = false
        }
        is UserRepository.LoginResult.BadRequest -> withContext(Dispatchers.Main) {
            _state.value = State.BadRequest
            _progressBarStatus.value = false
        }
        is UserRepository.LoginResult.GenericError -> withContext(Dispatchers.Main) {
            _state.value = State.GenericError
            _progressBarStatus.value = false
        }
        is UserRepository.LoginResult.NetworkError -> withContext(Dispatchers.Main) {
            _state.value = State.NetworkError
            _progressBarStatus.value = false
        }
        is UserRepository.LoginResult.ApiError -> withContext(Dispatchers.Main) {
            _state.value = State.ApiError
            _progressBarStatus.value = false
        }
    }

}


