package com.alkemy.ongandroid.businesslogic.di

import android.content.Context
import com.alkemy.ongandroid.businesslogic.managers.*
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

    @Singleton
    @Provides
    fun providesAnalyticsLogsManager(
        @ApplicationContext context: Context
    ): AnalyticsLogsManager {
        return AnalyticsLogsManagerImp(context)
    }

    @Provides
    fun providesValidatorImp(): Validator {
        return ValidatorImp()
    }
}