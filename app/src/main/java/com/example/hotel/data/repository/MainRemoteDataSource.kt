package com.example.hotel.data.repository

import android.util.Log
import com.example.hotel.data.Dto
import com.example.hotel.network.*
import com.example.hotel.network.NetworkResult.Companion.LAST_PAGE
import com.example.hotel.network.NetworkResult.Companion.LAST_PAGE_MESSAGE
import javax.inject.Inject

class MainRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    MainDataSource {
    override suspend fun getData(pagingCount: Int): NetworkResult<Dto> {
        Log.d("lastIndex", "$pagingCount")
        val result = apiService.getList(pagingCount)
        if (result is NetworkResult.Success) {
            if (result.resultData.data == null) {
                return NetworkResult.Error(LAST_PAGE, LAST_PAGE_MESSAGE)
            }
        }
        return result
    }
}