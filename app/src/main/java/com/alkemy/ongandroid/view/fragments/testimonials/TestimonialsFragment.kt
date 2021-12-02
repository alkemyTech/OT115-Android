package com.alkemy.ongandroid.view.fragments.testimonials

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.TestimonialsFragmentBinding

class TestimonialsFragment : Fragment() {

    private lateinit var binding: TestimonialsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TestimonialsFragmentBinding.bind(view)
    }

}