package com.alkemy.ongandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.api.ApiONGImp
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.model.ResponseLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localDataManager: LocalDataManager
) : ViewModel() {


    private val _loginfo = MutableLiveData<MutableList<ResponseLogin>>()
    val loginfo: LiveData<MutableList<ResponseLogin>>
        get() = _loginfo

    private val _progressBarStatus = MutableLiveData(false)
    val progressBarStatus
        get() = _progressBarStatus

    private fun getLogin(email: String, pass: String): Call<ResponseLogin> {
        return ApiONGImp().login(email, pass)
    }

    fun login(email: String, pass: String) {
        _progressBarStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("---", "*---")
            val resp = getLogin(email, pass).awaitResponse()
            if (resp.isSuccessful) {
                val info = resp.body()
                if (info != null) {
                    localDataManager.saveToken(info.data.token)
                    withContext(Dispatchers.Main) {
                        _loginfo.value = mutableListOf(info)
                    }
                }
            }
            withContext(Dispatchers.Main) {
                _progressBarStatus.value = false
            }
        }
    }

}
