package com.alkemy.ongandroid.businesslogic.di

import android.content.Context
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManager
import com.alkemy.ongandroid.businesslogic.managers.LocalDataManagerImp
import com.alkemy.ongandroid.businesslogic.managers.Validator
import com.alkemy.ongandroid.businesslogic.managers.ValidatorImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ManagerModule {

    @Singleton
    @Provides
    fun providesLocalDataManager(
        @ApplicationContext context: Context
    ): LocalDataManager {
        return LocalDataManagerImp(context)
    }

    @Provides
    fun providesValidatorImp(): Validator {
        return ValidatorImp()
    }
}