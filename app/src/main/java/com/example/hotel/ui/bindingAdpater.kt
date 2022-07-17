package com.example.hotel.ui

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DecimalFormat


@BindingAdapter("image")
fun setImage(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .load(url)
            .fitCenter()
            .into(view)
    }
}

@BindingAdapter("rate")
fun setRate(view: TextView, price: Double) {
    view.text = price.toString()
}


