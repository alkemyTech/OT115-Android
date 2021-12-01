package com.alkemy.ongandroid.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivityLoginBinding
import com.alkemy.ongandroid.databinding.ActivityLoginSuccessBinding

class LoginSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationBar()
    }

    fun setupBottomNavigationBar() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener() { item ->
            when (item.itemId) {
                R.id.news -> {
                    Toast.makeText(
                        this,
                        getString(R.string.nav_news),
                        Toast.LENGTH_LONG
                    ).show()
                    true
                }
                R.id.welcome -> {
                    Toast.makeText(
                        this,
                        getString(R.string.nav_welcome),
                        Toast.LENGTH_LONG
                    ).show()
                    true
                }
                R.id.stories -> {
                    Toast.makeText(
                        this,
                        getString(R.string.nav_stories),
                        Toast.LENGTH_LONG
                    ).show()
                    true
                }
                else -> false
            }
        }
    }
}