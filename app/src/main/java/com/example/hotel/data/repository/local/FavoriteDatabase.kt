package com.example.hotel.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hotel.data.ProductItem


@Database(entities = [ProductItem.Product::class], version = 2)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}