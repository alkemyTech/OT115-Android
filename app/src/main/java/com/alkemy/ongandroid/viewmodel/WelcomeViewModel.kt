package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.businesslogic.managers.AnalyticsLogsNewsTestimonialSlideManager
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepo
import com.alkemy.ongandroid.model.Slide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val repository: ApiRepo, private val analyticsSlider: AnalyticsLogsNewsTestimonialSlideManager) : ViewModel() {
    sealed class SlideStatus {
        class Loading(val isLoading: Boolean) : SlideStatus()
        class Success(val slideList: List<Slide>) : SlideStatus()
        class Failure(val error: Throwable) : SlideStatus()
    }

    private val _slideList = MutableLiveData<SlideStatus>()
    val slideList: LiveData<SlideStatus>
        get() = _slideList

    fun getSlides()
    {
        _slideList.value = SlideStatus.Loading(true)
        viewModelScope.launch {
            try {
                val response = repository.getSlides()
                if (response.success){
                    _slideList.value = SlideStatus.Success(response.slideList)
                } else{
                    _slideList.value = SlideStatus.Success(emptyList())
                }
            }catch (err: Throwable){
                _slideList.value = SlideStatus.Failure(err)
            }finally {
                _slideList.value = SlideStatus.Loading(false)
            }

        }

    }


    fun sliderSuccessEvent(){ analyticsSlider.sliderSuccessEvent() }

    fun sliderFailureEvent(){ analyticsSlider.sliderFailureEvent() }
}