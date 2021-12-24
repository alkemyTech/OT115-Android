package com.alkemy.ongandroid.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.FragmentWelcomeBinding
import com.alkemy.ongandroid.model.Slide
import com.alkemy.ongandroid.view.activities.BaseActivity
import com.alkemy.ongandroid.view.adapters.WelcomeViewPagerAdapter
import com.alkemy.ongandroid.viewmodel.WelcomeViewModel
import com.google.android.material.navigation.NavigationView
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
        (activity as BaseActivity).attachLoadingProgressBar(binding.root)
        loadSlides()
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
        handler.postDelayed(runnable, timeDelayAutoScrolling)
    }

    private fun loadSlides()
    {
        viewModel.getSlides()
    }

    private fun loadViewPagerAdapter(slideList: List<Slide>)
    {
        adapter = WelcomeViewPagerAdapter(slideList)
        binding.vpWelcome.adapter = adapter
    }

    private fun handleError(){

        val alertDialog: AlertDialog = activity.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(R.string.api_error_message)
                setPositiveButton(R.string.btn_text_try_again){dialog, _ ->
                    viewModel.getSlides()
                    dialog.dismiss()
                }
            }
            builder.create()
        }
        alertDialog.show()
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
                handler.postDelayed(runnable, timeDelayAutoScrolling)
            }
        })
    }

    private fun setUpObservers()
    {
        viewModel.slideList.observe(viewLifecycleOwner, {
            when (it) {
                is WelcomeViewModel.SlideStatus.Success -> {
                    viewModel.sliderSuccessEvent()
                    if (it.slideList.isEmpty()) {
                        activity?.findViewById<NavigationView>(R.id.navView)?.menu?.removeItem(R.id.welcome)
                    } else {
                        loadViewPagerAdapter(it.slideList)
                    }
                }
                is WelcomeViewModel.SlideStatus.Failure -> {
                    viewModel.sliderFailureEvent()
                    handleError()
                }
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner,{
            (activity as BaseActivity).setCustomProgressBarVisibility(it)
        })
    }
}