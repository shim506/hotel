package com.example.hotel.di

import com.example.hotel.network.ApiService
import com.example.hotel.network.RetrofitObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return RetrofitObject.createMainApi()
    }

}