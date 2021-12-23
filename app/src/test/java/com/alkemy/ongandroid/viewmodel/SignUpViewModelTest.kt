package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alkemy.ongandroid.MainCoroutineRule
import com.alkemy.ongandroid.businesslogic.managers.AnalyticsLogsManager
import com.alkemy.ongandroid.businesslogic.managers.Validator
import com.alkemy.ongandroid.businesslogic.repositories.UserRepository
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.UserRequest
import com.alkemy.ongandroid.util.getOrAwaitValue
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
class SignUpViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()
    private val userRepositoryMock: UserRepository = mock()
    private val validatorMock: Validator = mock()
    private val analyticsLogsManager: AnalyticsLogsManager = mock()
    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setUp() {
        viewModel = SignUpViewModel(userRepositoryMock, validatorMock, analyticsLogsManager)
    }

    @Test
    fun `given correct formatted email and password return true`() {
        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)
        whenever(validatorMock.validatePassword("Admin_123")).thenReturn(true)

        viewModel.validateFields(
            "martin",
            "admin@admin.com",
            "Admin_123",
            "Admin_123"
        )
        assertEquals(true, viewModel.isButtonSaveEnabled.getOrAwaitValue())
    }

    @Test
    fun `given equals password and confirmed password return true`() {
        viewModel.comparePasswords("i'mapassword", "i'mapassword")

        assertTrue(viewModel.arePasswordsTheSame.getOrAwaitValue())
    }

    @Test
    fun `given different password and confirmed password return true`() {
        viewModel.comparePasswords("i'mapassword", "i'mnotapassword")

        assertFalse(viewModel.arePasswordsTheSame.getOrAwaitValue())
    }

    @Test
    fun `given filled username, email and password but empty confirmed password return false`(){
        val username = "martin"
        val email = "admin@admin.com"
        val password = "I'm a password"
        val confirmedPassword = ""

        viewModel.validateFields(username, email, password, confirmedPassword)

        assertFalse(viewModel.isButtonSaveEnabled.getOrAwaitValue())
    }

    @Test
    fun `given filled username, email and confirmed password but empty password return false`(){
        val username = "martin"
        val email = "admin@admin.com"
        val password = ""
        val confirmedPassword = "I'm a password"

        viewModel.validateFields(username, email, password, confirmedPassword)

        assertFalse(viewModel.isButtonSaveEnabled.getOrAwaitValue())
    }

    @Test
    fun `given filled username, password and confirmed password but empty email return false`(){
        val username = "martin"
        val email = ""
        val password = "I'm a password"
        val confirmedPassword = "I'm a password"

        viewModel.validateFields(username, email, password, confirmedPassword)

        assertFalse(viewModel.isButtonSaveEnabled.getOrAwaitValue())
    }

    @Test
    fun `given filled email, password and confirmed password but empty username return false`(){
        val username = ""
        val email = "admin@admin.com"
        val password = "I'm a password"
        val confirmedPassword = "I'm a password"

        viewModel.validateFields(username, email, password, confirmedPassword)

        assertFalse(viewModel.isButtonSaveEnabled.getOrAwaitValue())
    }

    @Test
    fun `given a user to add, when the operation success then notify success`() = runBlockingTest {
        val user = mock<UserRequest>()
        val response = mock<NewUserResponse> { on { success } doReturn true }
        whenever(userRepositoryMock.addUserToRemoteDB(user)).thenReturn(response)

        viewModel.addUserToRemoteDB(user)

        assertEquals(SignUpViewModel.State.Success(response), viewModel.state.getOrAwaitValue())
    }

    @Test
    fun `given a user to add, when the operation fails then notify failure`() = runBlockingTest {
        val user = mock<UserRequest>()
        val response = mock<NewUserResponse> {
            on { message } doReturn "error"
            on { success } doReturn false
        }
        whenever(userRepositoryMock.addUserToRemoteDB(user)).thenReturn(response)

        viewModel.addUserToRemoteDB(user)

        val state = viewModel.state.getOrAwaitValue()
        assertTrue(state is SignUpViewModel.State.Failure)
        state as SignUpViewModel.State.Failure
        assertEquals("error", state.cause.message)

    }
}


/** the input is not valid if...
 * ...the username or password is empty
 * ...the username is already taken
 * ...the confirmed password doesn´t match the original one
 * ...email doesn´t validate format
 * ...password doesn´t validate format
 **/