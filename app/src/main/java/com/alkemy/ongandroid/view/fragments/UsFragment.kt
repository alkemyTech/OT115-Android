package com.alkemy.ongandroid.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alkemy.ongandroid.databinding.FragmentUsBinding
import com.alkemy.ongandroid.model.RowMembers
import com.alkemy.ongandroid.view.adapters.UsAdapter
import com.alkemy.ongandroid.viewmodel.UsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsFragment : Fragment() {

    private lateinit var binding: FragmentUsBinding
    var membersList = mutableListOf<RowMembers>()
    private val viewModel: UsFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        binding.rvUs.adapter = UsAdapter(membersList)
    }

    private fun setUpObservers() {
        viewModel.memberList.observe(viewLifecycleOwner) {
            membersList.addAll(it)
        }
    }

}