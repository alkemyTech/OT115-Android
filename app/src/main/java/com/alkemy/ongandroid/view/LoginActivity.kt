package com.alkemy.ongandroid.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.model.ResponseLogin
import com.alkemy.ongandroid.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var LoginVM: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Esta Activity esta seteada para ser la de inicio

        LoginVM = ViewModelProvider(this).get(LoginViewModel::class.java)

        GlobalScope.launch(Dispatchers.IO) {

            val resp = LoginVM.login("admin@admin","admin")


            withContext(Dispatchers.Main){

                saveToken(resp[0])
                Log.e("Data: ",resp[0].success)
            }
        }

    }

    //Esta parte esta bien realizarla aca o deberia hacerla en el VM?
    fun saveToken(resp: ResponseLogin){
        val sharedPref = getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putString("UserToken",resp.data.token)
        }.apply()
        Toast.makeText(this,"Token Guardado",Toast.LENGTH_LONG).show()
        Log.e("saved: ", resp.data.token)
    }

}