package com.alkemy.ongandroid.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpButtons()
    }
    private fun setUpButtons() {
        binding.btnSignUp.setOnClickListener {
            navigateToSignUpScreen()
        }

        binding.btnLogin.setOnClickListener{

            val resp:Boolean = true

            //funcion que le pega a la api
            //resp[0].success -> True
            //El else rompe porque si no es success no devuelve la misma data
            if(resp){
                navigateToMainScreen()
            }else{
                Toast.makeText(this,"Usuario o Contraseña incorrectos.",Toast.LENGTH_LONG).show()
            }

        }
    }
    private fun navigateToSignUpScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

    private fun navigateToMainScreen(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}