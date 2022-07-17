package com.example.hotel.ui.common

import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("checkBox")
fun setChecked(view: CheckBox, isCheck: Boolean) {
    view.isChecked = isCheck
}

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


