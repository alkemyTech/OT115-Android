package com.alkemy.ongandroid.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alkemy.ongandroid.databinding.ActivityMemberDetailBinding
import com.bumptech.glide.Glide

class MemberDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberDetailBinding

    companion object{
        private const val KEY_IMAGE: String = "image"
        private const val KEY_NAME: String = "name"
        private const val KEY_POSITION: String = "position"
        private const val KEY_FACEBOOK: String = "facebookURL"
        private const val KEY_LINKEDIN: String = "linkedinURL"

        fun createIntent(activity: Activity, image: String, name: String, position: String, facebookURL: String, linkedinURL: String): Intent
        {
            return Intent(activity, MemberDetailActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putString(KEY_IMAGE,image)
                    putString(KEY_NAME,name)
                    putString(KEY_POSITION,position)
                    putString(KEY_FACEBOOK,facebookURL)
                    putString(KEY_LINKEDIN,linkedinURL)
                }
                putExtras(bundle)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMemberInformation()
    }

    private fun loadMemberInformation()
    {
        intent.extras?.let { bundle ->
            Glide.with(this)
                .load(bundle.getString(KEY_IMAGE))
                .into(binding.imgMemberPhoto)

            binding.txtMemberName.text = bundle.getString(KEY_NAME)
            binding.txtMemberPosition.text = bundle.getString((KEY_POSITION))

            if (bundle.getString(KEY_FACEBOOK)!!.isNotEmpty())
            {
                binding.txtMemberFacebook.text = bundle.getString(KEY_FACEBOOK)
            }

            if (bundle.getString(KEY_LINKEDIN)!!.isNotEmpty())
            {
                binding.txtMemberFacebook.text = bundle.getString(KEY_LINKEDIN)
            }
        }
    }
}