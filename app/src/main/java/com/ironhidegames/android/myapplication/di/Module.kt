package com.ironhidegames.android.myapplication.di

import android.content.Context
import com.ironhidegames.android.myapplication.api.PlayerApiImplementation
import com.ironhidegames.android.myapplication.common.MySharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun providePrefs(@ApplicationContext context: Context): MySharedPreferences {
        return MySharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideApi(@ApplicationContext context: Context): PlayerApiImplementation {
        return PlayerApiImplementation(context)
    }

}