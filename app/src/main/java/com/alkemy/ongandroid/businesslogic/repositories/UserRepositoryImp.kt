package com.alkemy.ongandroid.businesslogic.repositories

import com.alkemy.ongandroid.businesslogic.api.OngApiService
import com.alkemy.ongandroid.model.LoginData
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.UserRequest

class UserRepositoryImp (private val remoteService: OngApiService): UserRepository {

    override suspend fun addUserToRemoteDB(userRequest: UserRequest): NewUserResponse {
        return remoteService.pushPost(userRequest)
    }

    override suspend fun logUser(login: LoginData): NewUserResponse {
        return remoteService.login(login)
    }
}