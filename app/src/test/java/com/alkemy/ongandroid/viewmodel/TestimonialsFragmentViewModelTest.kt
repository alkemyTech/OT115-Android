package com.alkemy.ongandroid.viewmodel

import com.alkemy.ongandroid.businesslogic.repositories.ApiRepo
import com.alkemy.ongandroid.businesslogic.repositories.UserRepository
import com.alkemy.ongandroid.util.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TestimonialsFragmentViewModelTest : TestCase() {

    private val apiRepo: ApiRepo = mock()
    private lateinit var viewModel: TestimonialsFragmentViewModel

    @Before
    public override fun setUp() {
        viewModel = TestimonialsFragmentViewModel(apiRepo)
    }

    @Test
    fun `Should set loading state to false when finished` () {
        viewModel.getTestimonials()
        assertEquals(false, viewModel.loadingState.getOrAwaitValue())
    }
}