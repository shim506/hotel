package com.example.hotel.ui

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hotel.R
import com.example.hotel.data.Product
import com.example.hotel.databinding.LodgingListItemBinding
import com.example.hotel.ui.LodgingListAdapter.LodgingListViewHolder

class LodgingListAdapter() :
    ListAdapter<Product, LodgingListViewHolder>(diffUtil) {

    inner class LodgingListViewHolder(private val binding: LodgingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LodgingListViewHolder {
        return LodgingListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.lodging_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LodgingListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}

