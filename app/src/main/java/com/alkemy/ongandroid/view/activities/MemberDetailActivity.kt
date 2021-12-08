package com.alkemy.ongandroid.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alkemy.ongandroid.databinding.ActivityMemberDetailBinding
import com.bumptech.glide.Glide

class MemberDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberDetailBinding
    private lateinit var infoMember: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMemberInformation()
    }

    private fun loadMemberInformation()
    {
        infoMember = intent.extras!!

        Glide.with(this)
            .load(infoMember.getString("image"))
            .into(binding.imgMemberPhoto)

        binding.txtMemberName.text = infoMember.getString("name")
        binding.txtMemberPosition.text = infoMember.getString(("position"))

        if (infoMember.getString("facebookURL") != "")
        {
            binding.txtMemberFacebook.text = infoMember.getString("facebookURL")
        }

        if (infoMember.getString("linkedinURL") != "")
        {
            binding.txtMemberFacebook.text = infoMember.getString("linkedinURL")
        }
    }
}