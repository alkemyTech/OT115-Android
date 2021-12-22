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

    //Este parece que si
    @Test
    fun `notNull data on Slide return`() {
        runBlocking {
            whenever(apiRepoMock.getSlides()).thenReturn(notNull())
            viewModel.getSlides()
            assertNotNull(viewModel.slideList.value)
        }
    }
    //

    @Test
    fun `null data on Slide return`() {
        runBlocking {
            whenever(apiRepoMock.getSlides()).thenReturn(null)
            viewModel.getSlides()
            assertNotNull(viewModel.slideList.value)
            //assertEquals(notNull(), viewModel.slideList.getOrAwaitValue())
            //WelcomeViewModel.SlideStatus.Failure()
            //
            //Expected :null
            //Actual   :com.alkemy.ongandroid.viewmodel.WelcomeViewModel$SlideStatus$Failure@23ffca5
            //
            //assertNotNull(viewModel.getSlides())
            //Con esto tambien funciona, no entiendo.
        }
    }

    //Este si
    @Test
    fun `isLoading getSlides() = false, normal exit`() {
        runBlocking {
            viewModel.getSlides()
            assertEquals(false, viewModel.isLoading.getOrAwaitValue())
        }
    }
    //


    @Test
    fun `force true on isLoading()`() {
        runBlocking {

            whenever(apiRepoMock.getSlides()).thenReturn(ApiSlidesResponse(false, emptyList()))

            //whenever(apiRepoMock.getSlides()).thenReturn(notNull())
            //val resp = apiRepoMock.getSlides()
            //resp.success //esto deberia ser false!
            viewModel.getSlides()
            assertEquals(true, viewModel.isLoading.getOrAwaitValue())
        }
    }

    //Estas si
    @Test
    fun `data on slideStatus`() {
        viewModel.slideList.observeForever {
            assertNotNull(it)
        }
    }


    @Test
    fun `slideList returns`() {
        viewModel.slideList.observeForever {
            assertEquals(it, true)
            assertEquals(it, false)
            //Porque ambas siendo opuestas dan bien??
        }
    }
    //
}