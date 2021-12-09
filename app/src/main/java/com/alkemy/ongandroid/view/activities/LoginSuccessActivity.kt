package com.alkemy.ongandroid.view.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivityLoginSuccessBinding
import com.alkemy.ongandroid.view.fragments.NewsFragment
import com.alkemy.ongandroid.view.fragments.TestimonialsFragment
import com.alkemy.ongandroid.view.fragments.UsFragment
import com.alkemy.ongandroid.view.fragments.WelcomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginSuccessActivity : BaseActivity() {

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
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,WelcomeFragment()).commit()
        navigationItemListener()
    }

    fun navigationItemListener() {
        binding.navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.welcome -> supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,WelcomeFragment()).commit()
                R.id.news -> supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,NewsFragment()).commit()
                R.id.stories ->supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,TestimonialsFragment()).commit()
                R.id.us ->supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,UsFragment()).commit()
            }
            binding.drawerLayout.closeDrawers()
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