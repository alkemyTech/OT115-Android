package com.alkemy.ongandroid.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
        setUpSignUpButton()

        LoginVM = ViewModelProvider(this).get(
            LoginViewModel::
            class.java
        )
        GlobalScope.launch(Dispatchers.IO) {
            setUpLogInButton()
        }


/*        GlobalScope.launch(Dispatchers.IO)
        {

            //admin@admin - pass: admin
            //val resp = LoginVM.login(binding.editTextEmail.text.toString() ,binding.editTextPassword.text.toString())

            withContext(Dispatchers.Main) {

                if(resp != null){
                    //Si da error tambien rompe aca...
                    saveToken(resp[0])
                    //si no es success va a romper!
                    setUpLogInButton(resp[0].success)
                }
            }
        }*/

    }


    private fun setUpSignUpButton() {
        binding.btnSignUp.setOnClickListener {
            navigateToSignUpScreen()
        }
    }

    suspend private fun setUpLogInButton() {
        binding.btnLogin.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                val resp = LoginVM.login(binding.editTextEmail.text.toString()
                    ,binding.editTextPassword.text.toString())

                withContext(Dispatchers.Main){
                    if (resp[0].success == "true") {
                        navigateToMainScreen()

                    } else {
                        Toast.makeText(applicationContext, "Usuario o Contraseña incorrectos.", Toast.LENGTH_LONG).show()
                    }
                }

            }

        }
    }

    private fun navigateToSignUpScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun saveToken(resp: ResponseLogin) {
        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putString("UserToken", resp.data.token)
        }.apply()
        Toast.makeText(this, "Token Guardado", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

}

