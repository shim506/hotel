package com.example.hotel.data.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.hotel.data.ProductItem

@Dao
interface ProductDao {
    @Insert
    fun insert(product: ProductItem.Product)

    @Delete
    fun delete(product: ProductItem.Product)

    @Query("SELECT * FROM Product")
    fun getAll(): List<ProductItem.Product>

    @Query("SELECT id FROM Product")
    fun getAllIds(): List<Int>
}