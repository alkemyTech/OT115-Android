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

class ActivitiesFragment: Fragment() {

    private lateinit var binding: FragmentActivitiesBinding
    private val activitiesList = mutableListOf<ActivitiesResp>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //aca deberia crear la data para que funke todx
        activitiesList.add(
            ActivitiesResp(0,"Titulo generico",""
            ,"lorem asd", "http://ongapi.alkemy.org//storage//FLQLzI8KqU.jpeg")
        )
        //ver si no rompe con esa info pasada

        binding.activitiesRv.adapter = ActivitiesAdapter(activitiesList)
    }

}