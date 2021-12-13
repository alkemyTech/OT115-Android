package com.alkemy.ongandroid.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alkemy.ongandroid.databinding.FragmentActivitiesBinding
import com.alkemy.ongandroid.model.ActivitiesResp
import com.alkemy.ongandroid.view.adapters.ActivitiesAdapter
import com.alkemy.ongandroid.viewmodel.ActivitiesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivitiesFragment: Fragment() {

    private lateinit var binding: FragmentActivitiesBinding
    private val activitiesList = mutableListOf<ActivitiesResp>()
    private val viewModel: ActivitiesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.actList.observe(viewLifecycleOwner){
            binding.activitiesRv.adapter = ActivitiesAdapter(it)
        }

    }

}