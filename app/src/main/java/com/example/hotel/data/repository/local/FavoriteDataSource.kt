package com.example.hotel.data.repository.local

import androidx.room.Query
import com.example.hotel.data.Dto
import com.example.hotel.data.ProductItem
import com.example.hotel.data.repository.MainDataSource
import com.example.hotel.network.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(
    private val dao: ProductDao
) {
    suspend fun getProducts(): List<ProductItem.Product> {
        return dao.getAll()
    }

    fun deleteProduct(product: ProductItem.Product) {
        return dao.delete(product)
    }

    fun insertProduct(product: ProductItem.Product) {
        return dao.insert(product)
    }

    suspend fun getProductIds(): List<Int> {
        return dao.getAllIds()
    }
}