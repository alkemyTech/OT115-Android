package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.alkemy.ongandroid.MainCoroutineRule
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepo
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepoImpl
import com.alkemy.ongandroid.businesslogic.repositories.WelcomeImagesRepository
import com.alkemy.ongandroid.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.notNull
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class WelcomeViewModelTest {

    //getSlides()
    //Adentro tiene
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val apiRepoMock: ApiRepo = mock()

    private lateinit var viewModel: WelcomeViewModel

    @Before
    fun setUp(){
        viewModel = WelcomeViewModel(apiRepoMock)
    }

    @Test
    fun `not null slide return`(){
        runBlocking {
            whenever(apiRepoMock.getSlides()).thenReturn(notNull())
        }
    }

    @Test
    fun `null slide return`(){
        runBlocking {
            whenever(apiRepoMock.getSlides()).thenReturn(null)
        }
    }

    @Test
    fun `prueba con vm success`(){

        runBlocking {

            assertEquals(viewModel.slideList, notNull())
            //assertEquals(viewModel.slideList.value, null)
        }
    }

    @Test
    fun `prueba con 2`(){
        runBlocking {
            whenever(viewModel.getSlides()).thenReturn(notNull())

        }
    }




}