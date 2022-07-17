package com.example.hotel.data.repository

import com.example.hotel.data.Data
import com.example.hotel.data.repository.MainDataSource
import com.example.hotel.network.NetworkResult
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainRemoteDataSource: MainDataSource) {
    suspend fun getData(): NetworkResult<Data> {
        return mainRemoteDataSource.getData()
    }
}