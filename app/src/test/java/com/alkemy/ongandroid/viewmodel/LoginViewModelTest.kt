package com.alkemy.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alkemy.ongandroid.MainCoroutineRule
import com.alkemy.ongandroid.businesslogic.managers.Validator
import com.alkemy.ongandroid.businesslogic.repositories.UserRepository
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
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginViewModelTest {


    //Estas 2 reglas son necesarias para corrutinas
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val userRepositoryMock: UserRepository = mock()

    private val validatorMock: Validator = mock()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(userRepositoryMock, validatorMock)
    }

    // Este es un ejemplo y podria hacerse ejemplos para cuando esten mal escritos mail o password
    @Test
    fun `given correct formatted email and password return true`() {

        // Siempre que se ejecute adentro del viewmodel "validator.validateEmail("admin@admin.com")" va a
        // devolver true
        Mockito
            .doReturn(true)
            .`when`(validatorMock)
            .validateEmail("admin@admin.com")

        // Siempre que se ejecute adentro del viewmodel "validator.validatePassword("Admin_123")" va a
        // devolver true
        Mockito
            .doReturn(true)
            .`when`(validatorMock)
            .validatePassword("Admin_123")

        viewModel.validateFields("admin@admin.com", "Admin_123")

        assertEquals(true, viewModel.viewState.getOrAwaitValue())
    }

    // Un ejemplo de otro test de el validateFields() podria ser con un mail incorrecto y a
    // Mockito pasarle un mail correcto, lo mismo tambien con la contraseña

    @Test
    fun `posting login return success`() {
        runBlocking {

            // Siempre que se ejecute adentro del viewmodel "userRepositoryMock.logUser()" va a
            // devolver UserRepository.LoginResult.Success
            userRepositoryMock
                .stub {
                    onBlocking { logUser(LoginData("admin@admin.com", "Admin_123")) }
                        .doReturn(UserRepository.LoginResult.Success(Data(UserApiResp(), "")))
                }

            viewModel.login("admin@admin.com", "Admin_123")
            // Si aca abajo se cambia el LoginViewModel.State.ApiError por LoginViewModel.State.Success el test anda
            assertEquals(LoginViewModel.State.Success, viewModel.state.getOrAwaitValue())
        }
    }

    // Un ejemplo de otro test seria otro caso de los LoginViewModel.State.*, habria que cubrir
    // todos los casos
}