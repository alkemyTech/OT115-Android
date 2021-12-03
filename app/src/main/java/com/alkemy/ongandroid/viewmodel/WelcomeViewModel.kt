package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.model.WelcomeImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val repository: WelcomeImagesRepository) : ViewModel() {
    fun getWelcomeImages(): List<Int> {
        return repository.getImages()
    }
}