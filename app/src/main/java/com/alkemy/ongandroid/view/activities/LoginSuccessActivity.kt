package com.alkemy.ongandroid.view.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
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
        this.setTitle(R.string.welcome)

        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, WelcomeFragment())
            .commit()
        navigationItemListener()
    }

    fun navigationItemListener() {
        binding.navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.welcome -> {
                    replaceFragment(WelcomeFragment())
                    this.setTitle(R.string.welcome)
                }
                R.id.news -> {
                    replaceFragment(NewsFragment())
                    this.setTitle(R.string.news)
                }
                R.id.stories -> {
                    replaceFragment(TestimonialsFragment())
                    this.setTitle(R.string.stories)
                }
                R.id.us -> {
                    replaceFragment(UsFragment())
                    this.setTitle(R.string.us)
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}