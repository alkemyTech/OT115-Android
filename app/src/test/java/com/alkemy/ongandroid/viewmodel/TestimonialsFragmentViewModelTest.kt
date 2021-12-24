package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alkemy.ongandroid.MainCoroutineRule
import com.alkemy.ongandroid.businesslogic.managers.AnalyticsLogsNewsTestimonialSlideManager
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepo
import com.alkemy.ongandroid.model.ApiTestimonialsResponse
import com.alkemy.ongandroid.util.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TestimonialsFragmentViewModelTest : TestCase() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val apiRepoMock: ApiRepo = mock()
    private lateinit var viewModel: TestimonialsFragmentViewModel
    private val successResponse = ApiTestimonialsResponse(true, emptyList())
    private val analyticsTestimonies: AnalyticsLogsNewsTestimonialSlideManager = mock()

    private val failureResponse = mock<ApiTestimonialsResponse> {
        on { success } doReturn false
        on { data } doReturn emptyList()
    }

    @Before
    public override fun setUp() {
        viewModel = TestimonialsFragmentViewModel(apiRepoMock,analyticsTestimonies)
    }

    @Test
    fun `Should set loading state to false when api call finished`() = runBlocking {
        whenever(apiRepoMock.getTestimonials()).thenReturn(successResponse)
        viewModel.getTestimonials()
        assertFalse(viewModel.loadingState.getOrAwaitValue())
    }

    @Test
    fun `Should set loadingState to true at least once`() = runBlocking {
        whenever(apiRepoMock.getTestimonials()).thenReturn(successResponse)
        viewModel.loadingState.observeForever {
            assertTrue(it)
        }
        viewModel.getTestimonials()
    }

    @Test
    fun `Should set state to Success when api call finished`() = runBlocking {
        whenever(apiRepoMock.getTestimonials()).thenReturn(successResponse)
        viewModel.getTestimonials()
        assertEquals(
            TestimonialsFragmentViewModel.State.Success(successResponse).javaClass,
            viewModel.state.getOrAwaitValue().javaClass
        )
    }

    @Test
    fun `Should set state to Failure when api call finished`() = runBlocking {
        whenever(apiRepoMock.getTestimonials()).thenReturn(failureResponse)
        viewModel.getTestimonials()
        assertEquals(
            TestimonialsFragmentViewModel.State.Failure(Throwable()).javaClass,
            viewModel.state.getOrAwaitValue().javaClass
        )
    }

}