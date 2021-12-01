package com.alkemy.ongandroid.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.R
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val DELAY_TIME = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTimer()
        getToken()

        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val token = sharedPref.getString("UserToken", "")

        if (token!=null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }
    }

    private fun startTimer() = GlobalScope.launch {
        delay(DELAY_TIME)
        withContext(Dispatchers.Main) {
            Toast.makeText(
                applicationContext,
                getString(R.string.timer_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getToken(){
        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val token= sharedPref.getString("UserToken","")
        Log.e("Token guardado: ",token.toString())
    }
}