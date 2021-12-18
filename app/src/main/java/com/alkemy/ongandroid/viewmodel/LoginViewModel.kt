package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.businesslogic.managers.AnalyticsLogsManager
import com.alkemy.ongandroid.businesslogic.managers.Validator
import com.alkemy.ongandroid.businesslogic.repositories.UserRepository
import com.alkemy.ongandroid.model.LoginData
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    private val validator: Validator,
    private val analyticsLogsManager: AnalyticsLogsManager
) : ViewModel() {

    companion object {
        const val PASSWORD_REGEX_WO_EC = "^" +
                "(?=.*[0-9])" +                 //at least 1 digit
                "(?=.*[a-zA-Z])" +              //any letter
                ".{4,}\$"
    }

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

    private val _signInOptions = MutableLiveData<GoogleSignInOptions>()
    val signInOptions: LiveData<GoogleSignInOptions>
        get() = _signInOptions

    fun login(email: String, pass: String) {
        _progressBarStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val resp = repository.logUser(LoginData(email, pass))
            handleLoginResponse(resp)
        }
    }

    fun validateFields(email: String, password: String) {

        val fieldsEmpty: Boolean = email == "" || password == ""
        val emailFormat: Boolean = validator.validateEmail(email)
        val passwordsFormat: Boolean = validator.validatePassword(password)

        _viewState.value = !fieldsEmpty && emailFormat && passwordsFormat
    }

    fun createSignInOptions() {
        val googleConfiguration = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        _signInOptions.value = googleConfiguration
    }

    private suspend fun handleLoginResponse(response: UserRepository.LoginResult) {
        withContext(Dispatchers.Main) {
            _progressBarStatus.value = false
            when (response) {
                is UserRepository.LoginResult.Success -> _state.value = State.Success
                is UserRepository.LoginResult.NoToken -> _state.value = State.Failure
                is UserRepository.LoginResult.BadRequest -> _state.value = State.BadRequest
                is UserRepository.LoginResult.GenericError -> _state.value = State.GenericError
                is UserRepository.LoginResult.NetworkError -> _state.value = State.NetworkError
                is UserRepository.LoginResult.ApiError -> _state.value = State.ApiError
            }
        }
    }

    fun registerLogInPressedEvent(){
        analyticsLogsManager.registerLogInPressedEvent()
    }

    fun registerSignUpPressedEvent(){
        analyticsLogsManager.registerSignUpPressedEvent()
    }

    fun registerGmailPressedEvent(){
        analyticsLogsManager.registerGmailPressedEvent()
    }

    fun registerFacebookPressedEvent(){
        analyticsLogsManager.registerFacebookPressedEvent()
    }

    fun registerLogInSuccessEvent(){
        analyticsLogsManager.registerLogInSuccessEvent()
    }

    fun registerLogInErrorEvent(){
        analyticsLogsManager.registerLogInErrorEvent()
    }
}


