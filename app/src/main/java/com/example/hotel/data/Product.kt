package com.example.hotel.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


sealed class ProductItem() {
    @Parcelize
    @Entity
    data class Product(
        @Embedded
        val description: Description,
        val id: Int,
        val name: String,
        val rate: Double,
        val thumbnail: String
    ) : Parcelable, ProductItem() {
        @PrimaryKey(autoGenerate = true)
        var registerIdx: Int = 0
    }

    class LoadingBar() : ProductItem()
}