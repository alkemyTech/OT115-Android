package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alkemy.ongandroid.MainCoroutineRule
import com.alkemy.ongandroid.businesslogic.managers.Validator
import com.alkemy.ongandroid.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
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
class ContactViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val validatorMock: Validator = mock()

    private lateinit var viewModel: ContactViewModel

    @Before
    fun setUp() {
        viewModel = ContactViewModel(validatorMock)
    }

    @Test
    fun `given correct formatted email a name and a message return true`() {

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)

        viewModel.validateFields("nombre completo", "admin@admin.com", "message")

        assertEquals(true, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(true, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(true, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given correct formatted email a name but no message return false`() {

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)

        viewModel.validateFields("nombre completo", "admin@admin.com", "")

        assertEquals(true, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(true, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(false, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given a name a message but no email return false`() {

        whenever(validatorMock.validateEmail("")).thenReturn(false)

        viewModel.validateFields("nombre completo", "", "message")

        assertEquals(true, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(false, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(true, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given correct formatted email a message but no name return false`() {

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)

        viewModel.validateFields("", "admin@admin.com", "message")

        assertEquals(false, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(true, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(true, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given a message a name but no correct formatted email return false`() {

        whenever(validatorMock.validateEmail("adminadmin.com")).thenReturn(false)

        viewModel.validateFields("", "adminadmin.com", "message")

        assertEquals(false, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(false, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(true, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given a message but no name and no email return false`() {

        whenever(validatorMock.validateEmail("")).thenReturn(false)

        viewModel.validateFields("", "", "message")

        assertEquals(false, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(false, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(true, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given a name but no message and no email return false`() {

        whenever(validatorMock.validateEmail("")).thenReturn(false)

        viewModel.validateFields("Nombre Completo", "", "")

        assertEquals(true, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(false, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(false, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given a correct formatted mail but no message and no name return false`() {

        whenever(validatorMock.validateEmail("admin@admin.com")).thenReturn(true)

        viewModel.validateFields("Nombre Completo", "admin@admin.com", "")

        assertEquals(true, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(true, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(false, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given a message but no name and wrong formatted email return false`() {

        whenever(validatorMock.validateEmail("adminadmin.com")).thenReturn(false)

        viewModel.validateFields("", "adminadmin.com", "message")

        assertEquals(false, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(false, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(true, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given a name but no message and wrong formatted email return false`() {

        whenever(validatorMock.validateEmail("adminadmin.com")).thenReturn(false)

        viewModel.validateFields("Nombre Completo", "adminadmin.com", "")

        assertEquals(true, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(false, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(false, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given a wrong formatted mail but no message and no name return false`() {

        whenever(validatorMock.validateEmail("adminadmin.com")).thenReturn(false)

        viewModel.validateFields("", "adminadmin.com", "")

        assertEquals(false, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(false, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(false, viewModel.isMessageValid.getOrAwaitValue())
    }

    @Test
    fun `given no mail no message and no name return false`() {

        whenever(validatorMock.validateEmail("")).thenReturn(false)

        viewModel.validateFields("", "", "")

        assertEquals(false, viewModel.isFullNameValid.getOrAwaitValue())
        assertEquals(false, viewModel.isValidEmail.getOrAwaitValue())
        assertEquals(false, viewModel.isMessageValid.getOrAwaitValue())
    }
}