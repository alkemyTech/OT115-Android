package com.alkemy.ongandroid.businesslogic.repositories

import com.alkemy.ongandroid.model.Data
import com.alkemy.ongandroid.model.LoginData
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.UserRequest

interface UserRepository {

    suspend fun addUserToRemoteDB(userRequest: UserRequest): NewUserResponse

    suspend fun logUser(login: LoginData): LoginResult

    sealed class LoginResult {
        object NetworkError : LoginResult()
        object ApiError : LoginResult()
        object BadRequest : LoginResult()
        class GenericError(val cause: Throwable) : LoginResult()
        class Success(val userData: Data) : LoginResult()
        object NoToken : LoginResult()
    }
}