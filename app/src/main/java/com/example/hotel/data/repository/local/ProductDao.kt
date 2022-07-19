package com.example.hotel.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hotel.data.ProductItem

@Dao
interface ProductDao {
    @Insert
    fun insert(product: ProductItem.Product)

    @Query("DELETE FROM Product WHERE id =:productId")
    fun deleteById(productId: Int)

    @Query("SELECT * FROM Product")
    fun getAll(): List<ProductItem.Product>

    @Query("SELECT id FROM Product")
    fun getAllIds(): List<Int>
}