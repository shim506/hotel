package com.example.hotel.data

import androidx.room.Entity

@Entity
data class Description(
    val imagePath: String,
    val price: Int,
    val subject: String
)