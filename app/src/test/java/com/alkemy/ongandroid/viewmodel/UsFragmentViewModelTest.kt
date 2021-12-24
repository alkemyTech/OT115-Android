package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alkemy.ongandroid.MainCoroutineRule
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepoImpl
import com.alkemy.ongandroid.core.Response
import com.alkemy.ongandroid.model.ApiMembersResponse
import com.alkemy.ongandroid.model.Member
import com.alkemy.ongandroid.util.getOrAwaitValue
import junit.framework.Assert.*
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.notNull
import org.mockito.kotlin.whenever
import java.lang.NullPointerException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class UsFragmentViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val repo: ApiRepoImpl = mock()
    private lateinit var viewModel: UsFragmentViewModel
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        viewModel = UsFragmentViewModel(repo)
        Dispatchers.setMain(dispatcher)
        coroutineRule.cleanupTestCoroutines()
    }

    @Test
    fun `set loading state to false when api call finished`(){
        runBlocking {
            viewModel.status.observeForever {
                assertFalse(it)
            }
            viewModel.getMembers()
        }
    }

    @Test
    fun `set loadingState to true at least once`(){
        runBlocking {
            viewModel.status.observeForever {
                assertTrue(it)
            }
            viewModel.getMembers()
        }
    }

    @Test
    fun `get members with successful response`() {
        runBlocking {
            whenever(repo.getMembers())
                .thenReturn(ApiMembersResponse(data = givenMembersList()))

            Assert.assertEquals(givenMembersList(), (viewModel.getMembers().getOrAwaitValue() as Response.Success).metaData.data)
        }
    }

    @Test
    fun `get members with failed response`() {
        runBlocking {
            whenever(repo.getMembers())
                .thenThrow(NullPointerException::class.java)

            Assert.assertEquals(Response.Failure<ApiMembersResponse>(Throwable()).javaClass, viewModel.getMembers().getOrAwaitValue().javaClass)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun givenMembersList() =
        listOf(
            Member("FotoPerfil","Roberto Sanchez","Contador")
        )
}