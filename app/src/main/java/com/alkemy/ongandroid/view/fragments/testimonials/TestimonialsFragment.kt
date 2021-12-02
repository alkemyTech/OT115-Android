package com.alkemy.ongandroid.view.fragments.testimonials

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkemy.ongandroid.databinding.TestimonialsFragmentBinding
import com.alkemy.ongandroid.model.Testimonial

class TestimonialsFragment : Fragment() {

    private lateinit var binding: TestimonialsFragmentBinding
    private lateinit var testimonialList: List<Testimonial>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TestimonialsFragmentBinding.bind(view)

        loadOfflineData()
        binding.recyclerView.adapter = TestimonialsAdapter(testimonialList)

    }

    private fun loadOfflineData() {
        testimonialList = listOf(
            Testimonial(
                "Marita Gomez",
                "http://ongapi.alkemy.org/storage/Mb7qJKMAWA.jpeg",
                "Acompañamos el proceso de transformación de las comunidades promoviendo la participación activa de todos sus integrantes como sujetos."
            ),
            Testimonial(
                "Josefa Prospero",
                "http://ongapi.alkemy.org/storage/vpXetNTugz.jpeg",
                "Una mención especial por el excelente trabajo académico realizado por mi tutor, el Dr. Walter Castro Aponte, agradeciéndole igualmente a él."
            ),
            Testimonial(
                "Marita Gomez",
                "http://ongapi.alkemy.org/storage/Mb7qJKMAWA.jpeg",
                "Acompañamos el proceso de transformación de las comunidades promoviendo la participación activa de todos sus integrantes como sujetos."
            ),
            Testimonial(
                "Josefa Prospero",
                "http://ongapi.alkemy.org/storage/vpXetNTugz.jpeg",
                "Una mención especial por el excelente trabajo académico realizado por mi tutor, el Dr. Walter Castro Aponte, agradeciéndole igualmente a él."
            ),
            Testimonial(
                "Marita Gomez",
                "http://ongapi.alkemy.org/storage/Mb7qJKMAWA.jpeg",
                "Acompañamos el proceso de transformación de las comunidades promoviendo la participación activa de todos sus integrantes como sujetos."
            ),
            Testimonial(
                "Josefa Prospero",
                "http://ongapi.alkemy.org/storage/vpXetNTugz.jpeg",
                "Una mención especial por el excelente trabajo académico realizado por mi tutor, el Dr. Walter Castro Aponte, agradeciéndole igualmente a él."
            )
        )
    }
}