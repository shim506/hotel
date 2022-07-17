package com.example.hotel.network

import com.example.hotel.data.Data
import retrofit2.http.GET


interface ApiService {
    @GET("1.json")
    suspend fun getList(): NetworkResult<Data>
}