package com.alkemy.ongandroid.businesslogic.di

import com.alkemy.ongandroid.model.WelcomeImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalImagesModule {
    @Provides
    fun getWelcomeImagesRepository(): WelcomeImagesRepository
    {
        return WelcomeImagesRepository()
    }
}