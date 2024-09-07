package com.ashishpatel.softwarelab.di

import android.app.Application
import com.ashishpatel.softwarelab.utils.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideNetConnection(application: Application): NetworkManager =
        NetworkManager(application)
}