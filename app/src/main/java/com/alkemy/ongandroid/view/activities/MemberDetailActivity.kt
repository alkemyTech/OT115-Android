package com.alkemy.ongandroid.view.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.databinding.ActivityMemberDetailBinding
import com.bumptech.glide.Glide

class MemberDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberDetailBinding
    private lateinit var linkedinURL: String
    private lateinit var facebookURL:String

    companion object{
        private const val KEY_IMAGE: String = "image"
        private const val KEY_NAME: String = "name"
        private const val KEY_POSITION: String = "position"
        private const val KEY_FACEBOOK: String = "facebookURL"
        private const val KEY_LINKEDIN: String = "linkedinURL"

        fun createIntent(activity: Context, image: String, name: String, position: String, facebookURL: String, linkedinURL: String): Intent
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
        setUpClickOnLinks()
    }

    private fun setUpClickOnLinks() {
        with(binding){
            txtMemberLinkedIn.setOnClickListener {
                setUpIntentToNavigate(linkedinURL)
            }
            txtMemberFacebook.setOnClickListener {
                setUpIntentToNavigate(facebookURL)
            }
        }
    }

    private fun setUpIntentToNavigate(text: String?) {
        val uri = Uri.parse(text)
        val uriIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(uriIntent)
    }

    private fun loadMemberInformation()
    {
        intent.extras?.let { bundle ->
            Glide.with(this)
                .load(bundle.getString(KEY_IMAGE))
                .into(binding.imgMemberPhoto)

            binding.txtMemberName.text = bundle.getString(KEY_NAME)
            binding.txtMemberPosition.text = bundle.getString((KEY_POSITION))

            if (bundle.getString(KEY_FACEBOOK)?.isNotEmpty() == true)
            {
                bundle.getString(KEY_FACEBOOK)?.let {
                    facebookURL = it
                    binding.txtMemberFacebook.text = it
                }
            }

            if (bundle.getString(KEY_LINKEDIN)?.isNotEmpty() == true)
            {
                bundle.getString(KEY_LINKEDIN)?.let {
                    linkedinURL = it
                    binding.txtMemberLinkedIn.text = it
                }
            }
        }
    }
}