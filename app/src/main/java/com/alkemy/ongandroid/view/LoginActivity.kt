package com.alkemy.ongandroid.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.model.ResponseLogin
import com.alkemy.ongandroid.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var LoginVM: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Esta Activity esta seteada para ser la de inicio

        LoginVM = ViewModelProvider(this).get(LoginViewModel::class.java)
        val resp = LoginVM.login("admin@admin","admin")
        Handler().postDelayed({
                              saveToken(resp[0])
        },2000)
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