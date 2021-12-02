package com.alkemy.ongandroid.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivityLoginSuccessBinding

class LoginSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSuccessBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.welcome -> Toast.makeText(this, R.string.nav_welcome, Toast.LENGTH_LONG).show()
                R.id.news -> Toast.makeText(this, R.string.nav_news, Toast.LENGTH_LONG).show()
                R.id.stories -> Toast.makeText(this, R.string.nav_stories, Toast.LENGTH_LONG).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

