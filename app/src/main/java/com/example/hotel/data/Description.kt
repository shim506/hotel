package com.example.hotel.data

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Description(
    val imagePath: String,
    val price: Int,
    val subject: String
): Parcelable