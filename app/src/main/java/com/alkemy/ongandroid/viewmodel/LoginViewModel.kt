package com.alkemy.ongandroid.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.businesslogic.PASSWORD_REGEX_WO_EC
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.businesslogic.repositories.UserRepository
import com.alkemy.ongandroid.model.LoginData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localDataManager: LocalDataManager,
    private val repository: UserRepository
) : ViewModel() {

    sealed class State {
        object Success : State()
        object Failure : State()
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

    private val _signInIntent = MutableLiveData<Intent>()
    val signInIntent: LiveData<Intent>
        get() = _signInIntent

    fun login(email: String, pass: String) {
        _progressBarStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val resp = repository.logUser(LoginData(email, pass))
            if (resp.success) {
                localDataManager.saveToken(resp.data.token)

                withContext(Dispatchers.Main) {
                    _state.value = State.Success
                    _progressBarStatus.value = false
                }
            } else {
                withContext(Dispatchers.Main) {
                    _state.value = State.Failure
                    _progressBarStatus.value = false
                }
            }
        }
    }

    fun validateFields(email: String, password: String) {

        val fieldsEmpty: Boolean = email == "" || password == ""
        val emailFormat: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordsFormat: Boolean = Pattern.matches(PASSWORD_REGEX_WO_EC, password)

        _viewState.value = !fieldsEmpty && emailFormat && passwordsFormat
    }

    fun createSignInIntent(activity: Activity) {
        val googleConfiguration = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(activity, googleConfiguration)
        _signInIntent.value = googleSignInClient.signInIntent
    }

}


