package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.*
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepoImpl
import com.alkemy.ongandroid.model.ApiTestimonialsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TestimonialsFragmentViewModel @Inject constructor(private val repo: ApiRepoImpl) : ViewModel() {
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
}