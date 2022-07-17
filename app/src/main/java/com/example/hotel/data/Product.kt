package com.example.hotel.data


sealed class ProductItem() {
    data class Product(
        val description: Description,
        val id: Int,
        val name: String,
        val rate: Double,
        val thumbnail: String,
        val favorite: Boolean = false
    ) : ProductItem()

     class LoadingBar() : ProductItem()
}