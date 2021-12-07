package com.alkemy.ongandroid.view.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.businesslogic.GOOGLE_SIGN_IN
import com.alkemy.ongandroid.core.toast
import com.alkemy.ongandroid.databinding.ActivityLoginBinding
import com.alkemy.ongandroid.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val loginVM: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var signInIntent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attachLoadingProgressBar(binding.mainView)
        setUpObservers()
        initializeComponents()
        setUpButtons()
        createSignInIntent()
    }

    private fun setUpObservers() {
        loginVM.state.observe(this, {
            when (it) {
                is LoginViewModel.State.Success -> navigateToMainScreen()
                //TODO refactorizar a un error
                is LoginViewModel.State.Failure -> toast(this, "Fallo el login")
            }
        })

        loginVM.progressBarStatus.observe(this) {
            setCustomProgressBarVisibility(it)
        }

        loginVM.viewState.observe(this, {
            when (it) {
                true -> enableLoginButton()
                false -> disableLoginButton()
            }
        })
        loginVM.signInIntent.observe(this){
            signInIntent = it
        }
    }

    private fun setUpButtons() {
        binding.btnSignUp.setOnClickListener {
            navigateToSignUpScreen()
        }
        binding.btnLogin.setOnClickListener {
            loginVM.login(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
        binding.btnSignUpGoogle.setOnclickListener{
            signInWithGoogle()
        }

    }

    private fun initializeComponents() {
        disableLoginButton()
        binding.editTextEmail.addTextChangedListener {
            loginVM.validateFields(binding.editTextEmail.text.toString(),  binding.editTextPassword.text.toString())
        }
        binding.editTextPassword.addTextChangedListener {
            loginVM.validateFields(binding.editTextEmail.text.toString(),  binding.editTextPassword.text.toString())
        }
    }

    private fun navigateToSignUpScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, LoginSuccessActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

    private fun disableLoginButton() {
        binding.btnLogin.isEnabled = false
        binding.btnLogin.setBackgroundColor(Color.LTGRAY)
    }

    private fun enableLoginButton() {
        binding.btnLogin.isEnabled = true
        binding.btnLogin.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.ong_color
            )
        )
    }
    private fun createSignInIntent() {
        loginVM.createSignInIntent(this)
    }

    private fun signInWithGoogle(){
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val googleSignInTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(googleSignInTask)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
//          to call the google account use: val account = completedTask.getResult(ApiException::class.java)
            navigateToMainScreen()
        } catch (e: ApiException) { toast(this, "Fallo el login") }
    }
}
