package com.alkemy.ongandroid.businesslogic.di

import com.alkemy.ongandroid.businesslogic.usescases.GetGoogleConfigurationUseCase
import com.alkemy.ongandroid.businesslogic.usescases.GetGoogleConfigurationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UsesCasesModule {

    @Provides
    fun providesGoogleSignInOptions(): GetGoogleConfigurationUseCase =
        GetGoogleConfigurationUseCaseImpl()

}