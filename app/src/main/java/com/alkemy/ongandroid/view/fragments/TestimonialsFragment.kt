package com.alkemy.ongandroid.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alkemy.ongandroid.databinding.TestimonialsFragmentBinding
import com.alkemy.ongandroid.model.Testimonial
import com.alkemy.ongandroid.view.adapters.TestimonialsAdapter
import com.alkemy.ongandroid.viewmodel.TestimonialsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestimonialsFragment : Fragment() {

    private lateinit var binding: TestimonialsFragmentBinding
    private val testimonialList = mutableListOf<Testimonial>()
    private val viewModel: TestimonialsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TestimonialsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        binding.recyclerView.adapter = TestimonialsAdapter(testimonialList)
    }

    private fun setUpObservers() {
        viewModel.testimonialList.observe(viewLifecycleOwner) {
            testimonialList.addAll(it)
        }
    }
}