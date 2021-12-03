package com.alkemy.ongandroid.view.fragments.testimonials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alkemy.ongandroid.databinding.TestimonialsFragmentBinding
import com.alkemy.ongandroid.model.Testimonial

class TestimonialsFragment : Fragment() {

    private lateinit var binding: TestimonialsFragmentBinding
    private lateinit var testimonialList: List<Testimonial>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestimonialsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadOfflineData()
        binding.recyclerView.adapter = TestimonialsAdapter(testimonialList)

    }

    private fun loadOfflineData() {
        testimonialList = listOf(
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