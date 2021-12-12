package com.alkemy.ongandroid.view.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivityLoginSuccessBinding
import com.alkemy.ongandroid.view.fragments.ActivitiesFragment
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

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.welcome -> Toast.makeText(this, R.string.nav_welcome, Toast.LENGTH_LONG).show()
                R.id.news -> Toast.makeText(this, R.string.nav_news, Toast.LENGTH_LONG)
                    .show()
                R.id.stories -> Toast.makeText(this, R.string.nav_stories, Toast.LENGTH_LONG)
                    .show()
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

