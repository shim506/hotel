package com.example.hotel.di


import android.content.Context
import androidx.room.Room
import com.example.hotel.data.repository.MainDataSource
import com.example.hotel.data.repository.MainRemoteDataSource
import com.example.hotel.data.repository.MainRepository
import com.example.hotel.data.repository.local.FavoriteDataSource
import com.example.hotel.data.repository.local.FavoriteDatabase
import com.example.hotel.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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


    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): FavoriteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoriteDatabase::class.java,
            "Product.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDataSource(
        favoriteDatabase: FavoriteDatabase
    ): FavoriteDataSource {
        return FavoriteDataSource(favoriteDatabase.productDao())
    }
}