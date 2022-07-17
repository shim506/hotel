package com.example.hotel.network

import com.example.hotel.data.Dto
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("{pageNumber}.json")
    suspend fun getList(@Path("pageNumber") pageNumber: Int = 1): NetworkResult<Dto>
}