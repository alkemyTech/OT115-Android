package com.alkemy.ongandroid.model

import com.alkemy.ongandroid.R
import javax.inject.Inject

class WelcomeImagesRepository @Inject constructor(){
    fun getImages(): List<Int> {
        return listOf(
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
    }
}