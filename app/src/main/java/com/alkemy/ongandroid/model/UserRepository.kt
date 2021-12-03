package com.alkemy.ongandroid.model

import com.alkemy.ongandroid.businesslogic.api.OngApiService
import javax.inject.Inject

class UserRepository @Inject constructor(private val remoteService: OngApiService) {

    suspend fun addUserToRemoteDB(userRequest: UserRequest): NewUserResponse {
        return remoteService.pushPost(userRequest)
    }

    suspend fun logUser(login: LoginData):NewUserResponse{
        return remoteService.login(login)
    }
}