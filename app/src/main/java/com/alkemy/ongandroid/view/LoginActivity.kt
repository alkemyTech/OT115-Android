package com.alkemy.ongandroid.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alkemy.ongandroid.model.ResponseLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.databinding.ActivityLoginBinding
import com.alkemy.ongandroid.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginVM: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpButtons()

        binding.btnLogin.setOnClickListener{
            loginVM.login(binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString())
        }
        //admin@admin - pass: admin

        setUpObservers()
    }

    private fun setUpObservers() {
        loginVM.loginfo.observe(this) {
            //Toast.makeText(this, it[0].data.user.email, Toast.LENGTH_LONG).show()
            //puedo hacer que eca se retorne un boolean si esta todx correcto
            //boolean que activa el boton para pasar de pagina.

            if(it[0].success == "true"){
                navigateToMainScreen()
            }
        }
    }


    private fun setUpButtons() {
        binding.btnSignUp.setOnClickListener {
            navigateToSignUpScreen()
        }
    }

    /*suspend private fun setUpLogInButton() {
        binding.btnLogin.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                val resp = LoginVM.login(
                    binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()
                )

                withContext(Dispatchers.Main) {
                    if (resp[0].success == "true") {
                        navigateToMainScreen()

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Usuario o Contraseña incorrectos.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }

        }
    }*/

    private fun navigateToSignUpScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

}