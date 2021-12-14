package com.alkemy.ongandroid.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.alkemy.ongandroid.viewmodel.ContactViewModel
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ContactFragmentBinding

class ContactFragment : Fragment() {

    private val viewModel by viewModels<ContactViewModel>()
    private lateinit var binding: ContactFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}