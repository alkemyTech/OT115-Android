package com.alkemy.ongandroid.view.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.core.toast
import com.alkemy.ongandroid.databinding.ActivityLoginBinding
import com.alkemy.ongandroid.viewmodel.LoginViewModel
import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import com.facebook.login.LoginResult
import org.json.JSONException

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val loginVM: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var signInOptions: GoogleSignInOptions
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attachLoadingProgressBar(binding.mainView)
        setUpObservers()
        initializeComponents()
        //Esto se puede limpiar mas?
        if(isLoggedInwfb()){
            navigateToMainScreen()
        }
        setUpButtons()
        createSignInOptions()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun isLoggedInwfb():Boolean{
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        return isLoggedIn
    }

    private fun setUpObservers() {
        loginVM.state.observe(this, {
            when (it) {
                is LoginViewModel.State.Success -> navigateToMainScreen()
                is LoginViewModel.State.Failure -> {
                    with(binding) {
                        textInputLayoutEmail.error = getString(R.string.incorrect_user_or_password)
                        textInputLayoutEmail.isErrorEnabled = true
                        editTextEmail.doAfterTextChanged { hideTextInputErrors() }
                        textInputLayoutPassword.error =
                            getString(R.string.incorrect_user_or_password)
                        textInputLayoutPassword.isErrorEnabled = true
                        editTextPassword.doAfterTextChanged { hideTextInputErrors() }
                    }
                }
                is LoginViewModel.State.BadRequest -> showLoginDialog(getString(R.string.bad_request))
                is LoginViewModel.State.GenericError -> showLoginDialog(getString(R.string.something_went_wrong))
                is LoginViewModel.State.NetworkError -> showLoginDialog(getString(R.string.check_your_internet_connection))
                is LoginViewModel.State.ApiError -> showLoginDialog(getString(R.string.api_error))
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
        loginVM.signInOptions.observe(this) {
            signInOptions = it
        }
    }

    private fun hideTextInputErrors() {
        with(binding) {
            textInputLayoutEmail.isErrorEnabled = false
            textInputLayoutPassword.isErrorEnabled = false
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
        binding.btnSignUpGoogle.setOnClickListener {
            signInWithGoogle()
        }

        setUpFbButton()

    }

    private fun setUpFbButton() {
        callbackManager = CallbackManager.Factory.create()
        binding.btnSignUpFb.setPermissions(listOf("email", "user_birthday"))
        binding.btnSignUpFb.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    val userId = loginResult.accessToken?.userId
                    Log.d(TAG, "onSuccess: $userId")

                    val bundle = Bundle()
                    bundle.putString(
                        "fields",
                        "id, email, first_name, last_name, gender, age_range"
                    )

                    navigateToMainScreen()
                    //No estaria pasando los datos por bundle ni seteandolos correctamente
                    //No esta llevandome a la main
                    val request =
                        GraphRequest.newMeRequest(loginResult.accessToken) { fbObject, response ->
                            Log.v("Login Success", response.toString())


                            try {
                                Log.d(TAG, "onSuccess: fbObject $fbObject")

                                Log.d(TAG, "onSuccess: nombre: ${fbObject.getString("first_name")}")
                                Log.d(TAG, "onSuccess: ${fbObject.getString("last_name")}")
                                Log.d(TAG, "onSuccess: ${fbObject.getString("gender")}")
                                Log.d(TAG, "onSuccess: ${fbObject.getString("email")}")

                            } catch (e: JSONException) {
                            }
                        }

                    request.parameters = bundle
                    request.executeAsync()

                }

                override fun onCancel() {
                    Log.d(TAG, "onCancel: called")
                }

                override fun onError(exception: FacebookException?) {
                    Log.d(TAG, "onError throw: $exception")
                }
            }
        )
    }

    private fun initializeComponents() {
        disableLoginButton()
        binding.editTextEmail.addTextChangedListener {
            loginVM.validateFields(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
        binding.editTextPassword.addTextChangedListener {
            loginVM.validateFields(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            )
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

    private fun createSignInOptions() {
        loginVM.createSignInOptions()
    }

    private val activityResult = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val signInTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(signInTask)
        }
    }

    private fun showLoginDialog(cause: String) {
        val layout = binding.root
        val snackbar = Snackbar.make(
            layout,
            cause,
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }

    private fun signInWithGoogle() {
        val googleSignInClient = GoogleSignIn.getClient(this, signInOptions)
        activityResult.launch(googleSignInClient.signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
//          to call the google account use: val account = completedTask.getResult(ApiException::class.java)
            navigateToMainScreen()
        } catch (e: ApiException) {
            toast(this, "Fallo el login")
        }
    }
}
