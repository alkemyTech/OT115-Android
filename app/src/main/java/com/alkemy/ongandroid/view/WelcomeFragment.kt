package com.alkemy.ongandroid.view

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.alkemy.ongandroid.view.adapters.WelcomeViewPagerAdapter
import com.alkemy.ongandroid.databinding.FragmentWelcomeBinding
import com.alkemy.ongandroid.viewmodel.SignUpViewModel
import com.alkemy.ongandroid.viewmodel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var adapter: WelcomeViewPagerAdapter
    private lateinit var runnable: Runnable
    private val handler: Handler = Handler()
    private val viewModel by viewModels<WelcomeViewModel>()

    companion object{
        private const val timeDelayAutoScrolling: Long = 3000
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadWelcomeImages()
        setUpObservers()
        changeCurrentItem()
        onNewItemSelected()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,timeDelayAutoScrolling)
    }

    private fun loadWelcomeImages()
    {
        viewModel.getWelcomeImages()
    }

    private fun loadViewPagerAdapter(listImages: List<Int>)
    {
        adapter = WelcomeViewPagerAdapter(listImages)
        binding.vpWelcome.adapter = adapter
    }

    private fun changeCurrentItem()
    {
        runnable = Runnable {
            binding.vpWelcome.currentItem = binding.vpWelcome.currentItem + 1
        }
    }

    private fun onNewItemSelected()
    {
        binding.vpWelcome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,timeDelayAutoScrolling)
            }
        })
    }

    private fun setUpObservers()
    {
        viewModel.welcomeImages.observe(viewLifecycleOwner, {
            when (it) {
                is WelcomeViewModel.WelcomeImages.Success -> loadViewPagerAdapter(it.listWelcomeImages)
            }
        })
    }
}