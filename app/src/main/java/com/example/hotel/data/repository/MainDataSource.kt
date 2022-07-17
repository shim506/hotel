package com.example.hotel.data.repository

import com.example.hotel.data.Dto
import com.example.hotel.network.NetworkResult


interface MainDataSource {
  suspend fun getData(pagingCount : Int): NetworkResult<Dto>
}