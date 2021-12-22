package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongandroid.businesslogic.managers.AnalyticsLogsNewsTestimonialSlideManager
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepo
import com.alkemy.ongandroid.model.ApiTestimonialsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestimonialsFragmentViewModel @Inject constructor(private val repo: ApiRepo, private val analyticsTestimonies: AnalyticsLogsNewsTestimonialSlideManager) : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    sealed class State {
        class Success(val response: ApiTestimonialsResponse) : State()
        class Failure(val cause: Throwable) : State()
    }

    fun getTestimonials() {
        viewModelScope.launch {
            val response = repo.getTestimonials()
            if (response.success) {
                _state.value = State.Success(response)
            } else {
                _state.value = State.Failure(Throwable(response.message))
            }
        }
    }

    fun testimoniesPressedEvent(){ analyticsTestimonies.testimoniesPressedEvent() }

    fun testimoniesSuccessEvent(){ analyticsTestimonies.testimoniesSuccessEvent() }

    fun testimoniesFailureEvent(){ analyticsTestimonies.testimoniesFailureEvent() }
}