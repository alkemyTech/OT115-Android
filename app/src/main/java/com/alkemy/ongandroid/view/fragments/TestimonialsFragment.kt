package com.alkemy.ongandroid.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.TestimonialsFragmentBinding
import com.alkemy.ongandroid.model.Testimonial
import com.alkemy.ongandroid.view.adapters.TestimonialsAdapter
import com.alkemy.ongandroid.viewmodel.TestimonialsFragmentViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestimonialsFragment : Fragment() {

    private lateinit var binding: TestimonialsFragmentBinding
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
        viewModel.getTestimonials()
        viewModel.testimoniesPressedEvent()
    }

    private fun setUpObservers() {
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is TestimonialsFragmentViewModel.State.Success -> {
                    viewModel.testimoniesSuccessEvent()
                    loadRecyclerView(it.response.data)
                }
                is TestimonialsFragmentViewModel.State.Failure -> {
                    viewModel.testimoniesFailureEvent()
                    apiErrorView()
                }
            }
        })
    }

    private fun loadRecyclerView(testimonialList: List<Testimonial>)
    {
        binding.recyclerView.adapter = TestimonialsAdapter(testimonialList)
    }

    private fun apiErrorView() {
        MaterialAlertDialogBuilder(requireActivity().applicationContext)
            .setTitle(R.string.error)
            .setMessage(R.string.api_error_message)
            .setPositiveButton(getString(R.string.retry)) { dialog, _ ->
                viewModel.getTestimonials()
                dialog.dismiss()
            }
            .show()
    }
}