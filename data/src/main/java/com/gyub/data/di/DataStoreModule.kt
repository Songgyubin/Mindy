package com.gyub.data.di

import android.content.Context
import com.gyub.data.datastore.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Data Store 모듈
 *
 * @author   Gyub
 * @created  2024/06/25
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providesUserPreferencesRepository(
        @ApplicationContext context: Context,
    ): UserPreferencesRepository {
        return UserPreferencesRepository(context)
    }
}