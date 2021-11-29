package com.alkemy.ongandroid.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alkemy.ongandroid.model.ResponseLogin
import com.alkemy.ongandroid.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.alkemy.ongandroid.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var LoginVM: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpButtons()

        LoginVM = ViewModelProvider(this).get(
            LoginViewModel::
            class.java
        )

        GlobalScope.launch(Dispatchers.IO)
        {

            val resp = LoginVM.login("admin@admin", "admin")

                saveToken(resp[0])
                getToken()

        }
    }


    private fun setUpButtons() {
        binding.btnSignUp.setOnClickListener {
            navigateToSignUpScreen()
        }
    }

    private fun navigateToSignUpScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    //Pasar al VM
    private fun saveToken(resp: ResponseLogin) {
        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putString("UserToken", resp.data.token)
        }.apply()
        Log.e("Token obtenido: ",resp.data.token)
        Toast.makeText(this, "Token Guardado", Toast.LENGTH_LONG).show()
    }

    private fun getToken(){
        val sharedPref = getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        val token= sharedPref.getString("UserToken","")
        Log.e("Token guardado: ",token.toString())
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

}
