package com.example.hotel.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


sealed class ProductItem() {

    @Entity
    data class Product(
        @Embedded
        val description: Description,
        val id: Int,
        val name: String,
        val rate: Double,
        val thumbnail: String
    ) : ProductItem() {
        @PrimaryKey(autoGenerate = true)
        var registerIdx: Int = 0
    }

    class LoadingBar() : ProductItem()
}