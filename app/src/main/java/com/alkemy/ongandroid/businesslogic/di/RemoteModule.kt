package com.alkemy.ongandroid.businesslogic.di

import com.alkemy.ongandroid.BuildConfig
import com.alkemy.ongandroid.businesslogic.api.OngApiService
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepo
import com.alkemy.ongandroid.businesslogic.repositories.ApiRepoImpl
import com.alkemy.ongandroid.businesslogic.repositories.UserRepository
import com.alkemy.ongandroid.businesslogic.repositories.UserRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun providesRetrofitClient(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.SERVER_URL)
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun providesONGApiService(retrofit: Retrofit): OngApiService {
        return retrofit.create(OngApiService::class.java)
    }

    @Provides
    fun providesUserRepository(remoteService: OngApiService): UserRepository {
        return UserRepositoryImp(remoteService)
    }

    @Provides
    fun providesApiRepo(remoteService: OngApiService): ApiRepo {
        return ApiRepoImpl(remoteService)
    }
}