package com.alkemy.ongandroid.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.core.Response
import com.alkemy.ongandroid.core.toast
import com.alkemy.ongandroid.databinding.FragmentUsBinding
import com.alkemy.ongandroid.model.Member
import com.alkemy.ongandroid.view.activities.BaseActivity
import com.alkemy.ongandroid.view.adapters.UsAdapter
import com.alkemy.ongandroid.viewmodel.UsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsFragment : Fragment() {

    private lateinit var binding: FragmentUsBinding
    private val viewModel: UsFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsBinding.inflate(inflater, container, false)

        buttonAction()
        fetchMembers()
        setObservers()
        (activity as? BaseActivity)?.attachLoadingProgressBar(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchMembers()
    }

    private fun initRecyclerView(list: List<Member>) {
        with(binding) {
            rvUs.isVisible = true
        }
        binding.rvUs.adapter = UsAdapter(list)
    }

    private fun fetchMembers() {
        viewModel.getMembers().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    initRecyclerView(it.metaData.data)
                }
                is Response.Failure -> {
                    errorActions()
                }
                else -> {}
            }
        }
    }

    private fun isLoading(isLoading: Boolean) {
        with(binding) {
            bTryAgain.isVisible = isLoading
            bTryAgain.isEnabled = !isLoading
            rvUs.isVisible = !isLoading
            (activity as? BaseActivity)?.setCustomProgressBarVisibility(isLoading)
        }
    }

    private fun errorActions() {
        toast(requireContext(), getString(R.string.api_error_message))
        with(binding) {
            bTryAgain.isVisible = true
            bTryAgain.isEnabled = true
        }
    }

    private fun buttonAction() {
        binding.bTryAgain.setOnClickListener {
            fetchMembers()
        }
    }

    private fun setObservers() {
        viewModel.status.observe(viewLifecycleOwner) {
            isLoading(it)
        }
    }
}