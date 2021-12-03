package com.alkemy.ongandroid.model

import com.alkemy.ongandroid.businesslogic.api.OngApiService
import javax.inject.Inject

class UserRepository @Inject constructor(private val remoteService: OngApiService) {

    suspend fun addUserToRemoteDB(user: User): NewUserResponse {
        return remoteService.pushPost(user)
    }

    suspend fun logUser(login: LoginData):NewUserResponse{
        return remoteService.login(login)
    }
}