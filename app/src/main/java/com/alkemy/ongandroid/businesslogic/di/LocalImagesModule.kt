package com.alkemy.ongandroid.businesslogic.di

import com.alkemy.ongandroid.businesslogic.repositories.WelcomeImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalImagesModule {
    @Provides
    fun getWelcomeImagesRepository(): WelcomeImagesRepository
    {
        return WelcomeImagesRepository()
    }
}