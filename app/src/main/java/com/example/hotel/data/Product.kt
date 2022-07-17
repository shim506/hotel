package com.example.hotel.data

data class Product(
    val description: Description,
    val id: Int,
    val name: String,
    val rate: Double,
    val thumbnail: String,
    val favorite: Boolean = false
)