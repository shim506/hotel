package com.example.hotel.di


import com.example.hotel.data.repository.MainDataSource
import com.example.hotel.data.repository.MainRepository
import com.example.hotel.data.repository.MainRemoteDataSource
import com.example.hotel.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteMainDataSource

    @Singleton
    @RemoteMainDataSource
    @Provides
    fun provideRemoteRepository(apiService: ApiService): MainDataSource {
        return MainRemoteDataSource(apiService)
    }

    @Provides
    fun provideRepository(
        @RemoteMainDataSource remoteDataSource: MainDataSource
    ): MainRepository {
        return MainRepository(remoteDataSource)
    }
}