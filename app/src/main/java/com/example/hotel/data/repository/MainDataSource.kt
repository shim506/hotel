package com.example.hotel.data.repository

import com.example.hotel.data.Data
import com.example.hotel.network.NetworkResult


interface MainDataSource {
  suspend fun getData(): NetworkResult<Data>
}