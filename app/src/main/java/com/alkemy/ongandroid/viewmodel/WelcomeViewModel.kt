package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.businesslogic.repositories.WelcomeImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val repository: WelcomeImagesRepository) : ViewModel() {
    sealed class WelcomeImages {
        class Success(val listWelcomeImages: List<Int>) : WelcomeImages()
    }

    private val _welcomeImages = MutableLiveData<WelcomeImages>()
    val welcomeImages: LiveData<WelcomeImages>
        get() = _welcomeImages

    fun getWelcomeImages()
    {
        _welcomeImages.value = WelcomeImages.Success(repository.getImages())
    }
}