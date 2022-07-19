package com.example.hotel.data.repository.local

import com.example.hotel.data.ProductItem
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(
    private val dao: ProductDao
) {
    suspend fun getProducts(): List<ProductItem.Product> {
        return dao.getAll()
    }

    fun deleteProduct(product: ProductItem.Product) {
        return dao.deleteById(product.id)
    }

    fun insertProduct(product: ProductItem.Product) {
        return dao.insert(product)
    }

    suspend fun getProductIds(): List<Int> {
        return dao.getAllIds()
    }
}