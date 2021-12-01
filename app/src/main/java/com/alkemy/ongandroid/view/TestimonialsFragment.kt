package com.alkemy.ongandroid.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alkemy.ongandroid.R

class TestimonialsFragment : Fragment() {

    companion object {
        fun newInstance() = TestimonialsFragment()
    }

    private lateinit var viewModel: TestimonialsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.testimonials_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestimonialsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}