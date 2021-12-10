package com.alkemy.ongandroid.businesslogic.repositories

import com.alkemy.ongandroid.businesslogic.api.OngApiService
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.model.LoginData
import com.alkemy.ongandroid.model.NewUserResponse
import com.alkemy.ongandroid.model.UserRequest
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImp(
    private val remoteService: OngApiService,
    private val localDataManager: LocalDataManager
) : UserRepository {

    override suspend fun addUserToRemoteDB(userRequest: UserRequest): NewUserResponse {
        return remoteService.pushPost(userRequest)
    }

    override suspend fun logUser(login: LoginData): UserRepository.LoginResult {

        return try {
            val response = remoteService.login(login)
            if (response.success) {
                localDataManager.saveToken(response.data.token)
                UserRepository.LoginResult.Success(response.data)
            } else {
                UserRepository.LoginResult.NoToken
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> UserRepository.LoginResult.NetworkError
                is HttpException -> when(throwable.code()) {
                    in 400..499 -> UserRepository.LoginResult.BadRequest
                    in 500..599 -> UserRepository.LoginResult.ApiError
                    else -> UserRepository.LoginResult.GenericError(throwable)
                }
                else -> UserRepository.LoginResult.GenericError(throwable)
            }
        }
    }
}