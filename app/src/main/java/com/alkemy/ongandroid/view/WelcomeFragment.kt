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
import com.alkemy.ongandroid.viewmodel.WelcomeViewModel

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
        adapter = WelcomeViewPagerAdapter(viewModel.getWelcomeImages())
        binding.vpWelcome.adapter = adapter
        runAutoScrolling()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,timeDelayAutoScrolling)
    }

    private fun runAutoScrolling()
    {
        runnable = Runnable {
            binding.vpWelcome.currentItem = binding.vpWelcome.currentItem + 1
        }
        binding.vpWelcome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,timeDelayAutoScrolling)
            }
        })
    }
}