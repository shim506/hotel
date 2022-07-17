package com.example.hotel.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


sealed class ProductItem() {

    @Entity
    data class Product(
        @Embedded
        val description: Description,
        @PrimaryKey
        val id: Int,
        val name: String,
        val rate: Double,
        val thumbnail: String
    ) : ProductItem()

    class LoadingBar() : ProductItem()
}