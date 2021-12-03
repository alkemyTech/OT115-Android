package com.alkemy.ongandroid.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.User
import com.alkemy.ongandroid.model.UserRepository
import com.alkemy.ongandroid.view.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
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

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private val _progressBarStatus = MutableLiveData(false)
    val progressBarStatus
        get() = _progressBarStatus

    fun addUserToRemoteDB(user: User) {
        _progressBarStatus.value = true
        viewModelScope.launch {
            val response = repository.addUserToRemoteDB(user)
            if (response.success) {
                _state.value = State.Success(response)
            }
            withContext(Dispatchers.Main) {
                _progressBarStatus.value = false
            }
        }
    }

    private val _isButtonEnabled = MutableLiveData(false)
    val isButtonSaveEnabled : LiveData<Boolean> = _isButtonEnabled

    fun validateFields(username:String, email:String, password: String, confirmPassword:String) {

        val fieldsEmpty: Boolean = username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
        val emailFormat: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordsFormat: Boolean = isValidPasswordFormat(password) && isValidPasswordFormat(confirmPassword)


        _isButtonEnabled.value = !fieldsEmpty && emailFormat && passwordsFormat
    }

    private val _arePasswordsTheSame = MutableLiveData(false)
    val arePasswordsTheSame : LiveData<Boolean> = _isButtonEnabled

    fun comparePasswords(password: String, confirmPassword: String) {
        _arePasswordsTheSame.value = (password == confirmPassword)
    }


    private fun isValidPasswordFormat(password: String): Boolean {
        return Pattern.matches(AppConstants.PASSWORD_REGEX, password)
    }

}