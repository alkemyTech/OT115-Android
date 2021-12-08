package com.alkemy.ongandroid.businesslogic.repositories

import com.alkemy.ongandroid.model.LoginData
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.UserRequest

interface UserRepository {

    suspend fun addUserToRemoteDB(userRequest: UserRequest): NewUserResponse

    suspend fun logUser(login: LoginData): NewUserResponse
}