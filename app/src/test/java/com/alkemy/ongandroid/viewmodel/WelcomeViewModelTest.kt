package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alkemy.ongandroid.MainCoroutineRule
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepo
import com.alkemy.ongandroid.model.ApiSlidesResponse
import com.alkemy.ongandroid.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.notNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class WelcomeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val apiRepoMock: ApiRepo = mock()

    private lateinit var viewModel: WelcomeViewModel

    @Before
    fun setUp() {
        viewModel = WelcomeViewModel(apiRepoMock)
    }


    @Test
    fun `notNull data on Slide return`() {
        runBlocking {
            whenever(apiRepoMock.getSlides()).thenReturn(notNull())
            viewModel.getSlides()
            assertNotNull(viewModel.slideList.value)
        }
    }

    @Test
    fun `null data on Slide return`() {
        runBlocking {
            whenever(apiRepoMock.getSlides()).thenReturn(null)
            viewModel.getSlides()
            assertNotNull(viewModel.slideList.value)
        }
    }

    @Test
    fun `isLoading getSlides() = false, normal exit`() {
        runBlocking {
            viewModel.getSlides()
            assertEquals(false, viewModel.isLoading.getOrAwaitValue())
        }
    }

    @Test
    fun `data on slideStatus`() {
        viewModel.slideList.observeForever {
            assertNotNull(it)
        }
    }

    @Test
    fun `slideList posible returns`() {
        viewModel.slideList.observeForever {
            assertEquals(it, true)
            assertEquals(it, false)
        }
    }

    @Test
    fun `loading posible returns`(){
        viewModel.isLoading.observeForever {
            assertEquals(false,it)
            assertEquals(true,it)
        }
    }
}