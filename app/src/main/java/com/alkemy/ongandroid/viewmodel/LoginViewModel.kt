package com.alkemy.ongandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.api.ApiONGImp
import com.alkemy.ongandroid.model.ResponseLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Handler

class LoginViewModel: ViewModel() {

    private var loginfo = ArrayList<ResponseLogin>()

    private fun getLogin(email: String, password: String): Call<ResponseLogin>{
        return ApiONGImp().Login(email,password)
    }

    //creo que la funcion de arriba no hace nada, revisar
    //pasarme a corrutinas?? Mmm...
    //por algun motivo tiene la info pero no la retorna
    //esta claro que la variable se modifica en otro ambito

    fun login(email: String, password:String ): ArrayList<ResponseLogin> {

        getLogin(email,password).enqueue(
            object : Callback<ResponseLogin>{
                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    val data = response.body()
                    Log.e("Hasta ahora: ",data!!.success)
                    if(response.isSuccessful){
                        loginfo.add(ResponseLogin(data.success,data.data,data.message))
                        Log.e("Hasta ahora: ",loginfo[0].success)
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    Log.e("Failed to Login: ", t.message.toString())
                }
            }
        )

        //Esto es un salvoconducto
        //Mi idea es corregirlo y pasarlo a corrutinas
        Handler().postDelayed({
            Log.e("Aber: ", loginfo[0].success)
        },2000)
        return loginfo
    }
}

