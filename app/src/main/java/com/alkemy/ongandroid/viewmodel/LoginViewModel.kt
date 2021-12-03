package com.alkemy.ongandroid.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.api.ApiONGImp
import com.alkemy.ongandroid.businesslogic.PASSWORD_REGEX_WO_EC
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.model.ResponseLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.awaitResponse
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localDataManager: LocalDataManager
) : ViewModel() {

    sealed class State {
        object Success : State()
        object Failure : State()
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private val _viewState = MutableLiveData(false)
    val viewState
        get() = _viewState

    private val _progressBarStatus = MutableLiveData(false)
    val progressBarStatus
        get() = _progressBarStatus

    private fun getLogin(email: String, pass: String): Call<ResponseLogin> {
        return ApiONGImp().login(email, pass)
    }

    fun login(email: String, pass: String) {
        _progressBarStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val resp = getLogin(email, pass).awaitResponse()
            if (resp.isSuccessful) {
                val info = resp.body()
                if (info?.data != null) {
                    localDataManager.saveToken(info.data.token)
                    withContext(Dispatchers.Main) {
                        _state.value = State.Success
                    }
                }
            }
            withContext(Dispatchers.Main) {
                _progressBarStatus.value = false
            }
        }
    }

    fun validateFields(email: String, password: String) {

        val fieldsEmpty: Boolean = email == "" || password == ""
        val emailFormat: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordsFormat: Boolean = Pattern.matches(PASSWORD_REGEX_WO_EC, password)

        _viewState.value = !fieldsEmpty && emailFormat && passwordsFormat

    }

}


