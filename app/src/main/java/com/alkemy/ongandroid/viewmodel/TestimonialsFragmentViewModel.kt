package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.model.Testimonial
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class TestimonialsFragmentViewModel : ViewModel() {
    private val _testimonialList = MutableLiveData<List<Testimonial>>(arrayListOf())
    val testimonialList: LiveData<List<Testimonial>> get() = _testimonialList

    init {
        fetchData()
    }

    private fun fetchData() {
        _testimonialList.value = listOf(
            Testimonial(
                "http://ongapi.alkemy.org/storage/Mb7qJKMAWA.jpeg",
                "Marita Gomez",
                "Acompañamos el proceso de transformación de las comunidades promoviendo la participación activa de todos sus integrantes como sujetos."
            ),
            Testimonial(
                "http://ongapi.alkemy.org/storage/vpXetNTugz.jpeg",
                "Josefa Prospero",
                "Una mención especial por el excelente trabajo académico realizado por mi tutor, el Dr. Walter Castro Aponte, agradeciéndole igualmente a él."
            ),
            Testimonial(
                "http://ongapi.alkemy.org/storage/Mb7qJKMAWA.jpeg",
                "Marita Gomez",
                "Acompañamos el proceso de transformación de las comunidades promoviendo la participación activa de todos sus integrantes como sujetos."
            ),
            Testimonial(
                "http://ongapi.alkemy.org/storage/Mb7qJKMAWA.jpeg",
                "Marita Gomez",
                "Acompañamos el proceso de transformación de las comunidades promoviendo la participación activa de todos sus integrantes como sujetos."
            ),
            Testimonial(
                "http://ongapi.alkemy.org/storage/vpXetNTugz.jpeg",
                "Josefa Prospero",
                "Una mención especial por el excelente trabajo académico realizado por mi tutor, el Dr. Walter Castro Aponte, agradeciéndole igualmente a él."
            ),
            Testimonial(
                "http://ongapi.alkemy.org/storage/Mb7qJKMAWA.jpeg",
                "Marita Gomez",
                "Acompañamos el proceso de transformación de las comunidades promoviendo la participación activa de todos sus integrantes como sujetos."
            ),
        )
    }
}