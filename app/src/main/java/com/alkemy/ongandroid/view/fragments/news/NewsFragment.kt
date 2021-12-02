package com.alkemy.ongandroid.view.fragments.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.core.Response
import com.alkemy.ongandroid.core.toast
import com.alkemy.ongandroid.databinding.FragmentNewsBinding
import com.alkemy.ongandroid.model.ApiNews
import com.alkemy.ongandroid.viewmodel.ApiDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel by viewModels<ApiDataViewModel>()
    private val newsAdapter by lazy { NewsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        fetchNews()
        buttonAction()
    }

    private fun fetchNews() {
        newsViewModel.getNews().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> {
                    setProgressBar()
                }
                is Response.Success -> {
                    setRecyclerView(it.metaData.data)
                }
                is Response.Failure -> {
                    errorActions()
                }
            }
        }
    }

    private fun errorActions() {
        toast(requireContext(), getString(R.string.api_error_message))
        with(binding){
            btnTryAgain.isVisible = true
            btnTryAgain.isEnabled = true
        }
    }

    private fun setProgressBar() {
        with(binding){
            btnTryAgain.isVisible = false
            btnTryAgain.isEnabled = false
            rvNews.isVisible = false
            progressBar.isVisible = true
        }
    }

    private fun setRecyclerView(data: List<ApiNews>) {
        with(binding) {
            progressBar.isVisible = false
            rvNews.isVisible = true
            rvNews.adapter = newsAdapter
        }
        newsAdapter.setData(data.toMutableList())
    }

    private fun buttonAction() {
        binding.btnTryAgain.setOnClickListener {
            fetchNews()
        }
    }
}