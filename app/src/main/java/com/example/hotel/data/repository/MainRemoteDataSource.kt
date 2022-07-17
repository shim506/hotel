package com.example.hotel.data.repository

import android.util.Log
import com.example.hotel.data.Data
import com.example.hotel.network.ApiService
import com.example.hotel.network.NetworkResult
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MainRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    MainDataSource {
    override suspend fun getData(): NetworkResult<Data> {
        // TODO
        Log.d("result111", "a")
        val result = apiService.getList()
        if (result is NetworkResult.Success) {
            Log.d("result111", result.resultData.product.toString())
        }else if (result is NetworkResult.Exception){
            Log.d("result111", "exceptjion")
        }else if (result is NetworkResult.Error){
            Log.d("result111", "error")
        }
        return result
    }
}
