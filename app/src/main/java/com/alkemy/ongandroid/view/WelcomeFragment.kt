package com.alkemy.ongandroid.view

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.businesslogic.adapters.WelcomeViewPagerAdapter
import com.alkemy.ongandroid.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var adapter: WelcomeViewPagerAdapter
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listPhotosId = listOf(
            R.drawable.foto1,
            R.drawable.foto2,
            R.drawable.foto3,
            R.drawable.foto4,
            R.drawable.foto5,
            R.drawable.foto6,
            R.drawable.foto6_1,
            R.drawable.foto10,
            R.drawable.foto11,
            R.drawable.manos10)

        adapter = WelcomeViewPagerAdapter(listPhotosId)
        binding.vpWelcome.adapter = adapter

        handler = Handler()
        runnable = Runnable {
            binding.vpWelcome.currentItem = binding.vpWelcome.currentItem + 1
        }
        binding.vpWelcome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,3000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,3000)
    }
}