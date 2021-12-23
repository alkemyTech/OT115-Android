package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alkemy.ongandroid.MainCoroutineRule
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepo
import com.alkemy.ongandroid.model.ApiSlidesResponse
import com.alkemy.ongandroid.model.Slide
import com.alkemy.ongandroid.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
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
import java.lang.NullPointerException

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
    fun `getSlide returns notNull on hardcoded data`() {
        runBlocking {
            whenever(apiRepoMock.getSlides()).thenReturn(givenApiSlideResp())
            viewModel.getSlides()
            assertNotNull(viewModel.slideList.getOrAwaitValue())
        }
    }

    @Test
    fun `when getSlides call isLoading false, normal exit`() {
        runBlocking {
            viewModel.getSlides()
            assertEquals(false, viewModel.isLoading.getOrAwaitValue())
        }
    }

    @Test
    fun `when getSlides call isLoading expected true`() {
        viewModel.isLoading.observeForever {
            assertEquals(it, true)
        }
        viewModel.getSlides()
    }

    @Test
    fun `when getSlides return nullPointerExcep slideStatus fall in Failure`(){
        runBlocking {
            whenever(apiRepoMock.getSlides()).thenThrow(NullPointerException::class.java)
            viewModel.getSlides()
            assertEquals(WelcomeViewModel.SlideStatus.Failure(Throwable()).javaClass
                , viewModel.slideList.getOrAwaitValue().javaClass)
        }
    }
}

private fun givenSlideList() =
    listOf(Slide("aa","vbb","cac"))

private fun givenApiSlideResp() = ApiSlidesResponse(true, givenSlideList())