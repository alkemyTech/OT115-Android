package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.util.getOrAwaitValue
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SplashViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val localDataManager: LocalDataManager = mock()
    private lateinit var viewModel: SplashViewModel

    @Before
    fun setUp() {
        viewModel = SplashViewModel(localDataManager)
    }

    @Test
    fun `get token with an non-empty string returned`(){
        whenever(localDataManager.getToken()).thenReturn("UserToken")
        viewModel.getToken()
        assertTrue(viewModel.existToken.getOrAwaitValue())
    }

    @Test
    fun `get token with an empty string returned`(){
        whenever(localDataManager.getToken()).thenReturn("")
        viewModel.getToken()
        assertFalse(viewModel.existToken.getOrAwaitValue())
    }
}