package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alkemy.ongandroid.MainCoroutineRule
import com.alkemy.ongandroid.businesslogic.managers.Validator
import com.alkemy.ongandroid.businesslogic.repositories.UserRepository
import com.alkemy.ongandroid.businesslogic.usescases.GetGoogleConfigurationUseCase
import com.alkemy.ongandroid.model.Data
import com.alkemy.ongandroid.model.LoginData
import com.alkemy.ongandroid.model.UserApiResp
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
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val userRepositoryMock: UserRepository = mock()

    private val validatorMock: Validator = mock()

    private val getGoogleSignInOptions: GetGoogleConfigurationUseCase = mock()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(userRepositoryMock, validatorMock, getGoogleSignInOptions)
    }

    @Test
    fun `given correct formatted email and password return true`() {

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)
        whenever(validatorMock.validatePassword("Admin_123")).thenReturn(true)

        viewModel.validateFields("admin@admin.com", "Admin_123")

        assertEquals(true, viewModel.viewState.getOrAwaitValue())
    }

    @Test
    fun `given an incorrect formatted email with out dotcom and correct password return false`(){

        whenever(validatorMock.validateEmail("admin@admin")).thenReturn(false)
        whenever(validatorMock.validatePassword("Admin_123")).thenReturn(true)

        viewModel.validateFields("admin@admin", "Admin_123")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }

    @Test
    fun `given incorrect formatted email and correct password return false`(){

        whenever(validatorMock.validateEmail("adminadmin.com")).thenReturn(false)
        whenever(validatorMock.validatePassword("Admin_123")).thenReturn(true)

        viewModel.validateFields("admin@admin", "Admin_123")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }

    @Test
    fun `given correct formatted email and incorrect password return false`(){

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)
        whenever(validatorMock.validatePassword("elefante")).thenReturn(false)

        viewModel.validateFields("admin@admin.com", "elefante")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }

    @Test
    fun `given correct formatted email and password too short return false`(){

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)
        whenever(validatorMock.validatePassword("12")).thenReturn(false)

        viewModel.validateFields("admin@admin.com", "12")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }

    @Test
    fun `given correct formatted email and password with out letters return false`(){

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)
        whenever(validatorMock.validatePassword("123456_")).thenReturn(false)

        viewModel.validateFields("admin@admin.com", "123456_")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }

    @Test
    fun `given correct formatted email and password with out special chars return false`(){

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)
        whenever(validatorMock.validatePassword("Admin123")).thenReturn(false)

        viewModel.validateFields("admin@admin.com", "Admin123")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }
    @Test
    fun `given correct formatted email and password with out upper case return false`(){

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)
        whenever(validatorMock.validatePassword("admin_123")).thenReturn(false)

        viewModel.validateFields("admin@admin.com", "admin_123")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }
    @Test
    fun `given correct formatted email and a password with only numbers return false`(){

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)
        whenever(validatorMock.validatePassword("123456789")).thenReturn(false)

        viewModel.validateFields("admin@admin.com", "admin123")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }

    @Test
    fun `given incorrect formatted email and password return false`(){

        whenever(validatorMock.validateEmail("fake@admin")).thenReturn(false)
        whenever(validatorMock.validatePassword("elefante")).thenReturn(false)

        viewModel.validateFields("fake@admin", "elefante")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }
    @Test
    fun `given incorrect formatted email and password with correct mocked values return false`(){

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)
        whenever(validatorMock.validatePassword("Admin_123")).thenReturn(true)

        viewModel.validateFields("fake@admin", "elefante")

        assertEquals(false, viewModel.viewState.getOrAwaitValue())
    }


    @Test
    fun `posting login return Success`() {
        runBlocking {
            whenever(userRepositoryMock.logUser(LoginData("admin@admin.com", "Admin_123")))
                .thenReturn(UserRepository.LoginResult.Success(Data(UserApiResp(), "")))

            viewModel.login("admin@admin.com", "Admin_123")
            assertEquals(LoginViewModel.State.Success, viewModel.state.getOrAwaitValue())
        }
    }

    @Test
    fun `posting login return Failure`(){
        runBlocking {
            whenever(userRepositoryMock.logUser(LoginData("fakeAdmin@admin.com", "Admin_123")))
                .thenReturn(UserRepository.LoginResult.NoToken)

            viewModel.login("fakeAdmin@admin.com", "Admin_123")
            assertEquals(LoginViewModel.State.Failure, viewModel.state.getOrAwaitValue())
        }
    }

    @Test
    fun `posting login return BadRequest`(){
        runBlocking {
            whenever(userRepositoryMock.logUser(LoginData("fakeAdmin@admin.com", "Admin_123")))
                .thenReturn(UserRepository.LoginResult.BadRequest)

            viewModel.login("fakeAdmin@admin.com", "Admin_123")
            assertEquals(LoginViewModel.State.BadRequest, viewModel.state.getOrAwaitValue())
        }
    }

    @Test
    fun `posting login return GenericError`(){
        runBlocking {
            whenever(userRepositoryMock.logUser(LoginData("fakeAdmin@admin.com", "Admin_123")))
                .thenReturn(UserRepository.LoginResult.GenericError(Throwable()))

            viewModel.login("fakeAdmin@admin.com", "Admin_123")
            assertEquals(LoginViewModel.State.GenericError, viewModel.state.getOrAwaitValue())
        }
    }

    @Test
    fun `posting login return NetworkError`(){
        runBlocking {
            whenever(userRepositoryMock.logUser(LoginData("admin@admin.com", "Admin_123")))
                .thenReturn(UserRepository.LoginResult.NetworkError)

            viewModel.login("admin@admin.com", "Admin_123")
            assertEquals(LoginViewModel.State.NetworkError, viewModel.state.getOrAwaitValue())
        }
    }

    @Test
    fun `posting login return ApiError`(){
        runBlocking {
            whenever(userRepositoryMock.logUser(LoginData("admin@admin.com", "Admin_123")))
                .thenReturn(UserRepository.LoginResult.ApiError)

            viewModel.login("admin@admin.com", "Admin_123")
            assertEquals(LoginViewModel.State.ApiError, viewModel.state.getOrAwaitValue())
        }
    }

    @Test
    fun `create sign in options it's created once`(){

        viewModel.createSignInOptions()
        verify(getGoogleSignInOptions, times(1)).invoke()
    }
}