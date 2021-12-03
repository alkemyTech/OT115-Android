package com.alkemy.ongandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.api.ApiONGImp
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.model.LoginData
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.ResponseLogin
import com.alkemy.ongandroid.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localDataManager: LocalDataManager,
    private val repository: UserRepository
) : ViewModel() {


    private val _loginfo = MutableLiveData<MutableList<NewUserResponse>>()
    val loginfo: LiveData<MutableList<NewUserResponse>>
        get() = _loginfo

//    private fun getLogin(email: String, pass: String): Call<ResponseLogin> {
//        return ApiONGImp().login(email, pass)
//    }

    fun login(email: String, pass: String) {

        viewModelScope.launch(Dispatchers.IO) {
/*            val resp = getLogin(email, pass).awaitResponse()
            if (resp.isSuccessful) {
                val info = resp.body()
                if (info != null) {
                    localDataManager.saveToken(info.data.token)
                    withContext(Dispatchers.Main) {
                        _loginfo.value = mutableListOf(info)
                    }
                }
            }*/
            val resp = repository.logUser(LoginData(email,pass))
            if(resp.success){
                localDataManager.saveToken(resp.data.token)
                Log.e("token: ", resp.data.token)
                withContext(Dispatchers.Main){
                    _loginfo.value = mutableListOf(resp)
                }
            }
        }

    }


}

