package com.example.hotel.data.repository


import com.example.hotel.data.Dto

import com.example.hotel.network.NetworkResult
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainRemoteDataSource: MainDataSource) {
    suspend fun getData(pagingCount : Int): NetworkResult<Dto> {
        return mainRemoteDataSource.getData(pagingCount)
    }
}