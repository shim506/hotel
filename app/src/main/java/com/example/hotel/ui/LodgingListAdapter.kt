package com.example.hotel.ui


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hotel.R
import com.example.hotel.data.ProductItem
import com.example.hotel.databinding.ItemLoadingBinding
import com.example.hotel.databinding.LodgingListItemBinding

class LodgingListAdapter(val listener: AdapterItemTouchListener) :
    ListAdapter<ProductItem, RecyclerView.ViewHolder>(diffUtil) {

    inner class LodgingListItemViewHolder(private val binding: LodgingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductItem.Product) {
            binding.item = item

            binding.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) listener.checkFavorite(item)
                else listener.cancelFavorite(item)
            }
        }
    }

    inner class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProductItem.Product -> VIEW_TYPE_ITEM
            is ProductItem.LoadingBar -> VIEW_TYPE_LOADING
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("test11", "1100")
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                LodgingListItemViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.lodging_list_item,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_LOADING -> {
                LoadingViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_loading,
                        parent,
                        false
                    )
                )
            }
            else -> error("View Type을 찾을 수 없습니다.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LodgingListItemViewHolder -> {
                holder.bind(getItem(position) as ProductItem.Product)
            }
            is LoadingViewHolder -> {
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ProductItem>() {
            override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return when (oldItem) {
                    is ProductItem.Product -> oldItem.id == (newItem as? ProductItem.Product)?.id ?: false
                    is ProductItem.LoadingBar -> oldItem == (newItem as? ProductItem.LoadingBar) ?: false
                }
            }

            override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem == newItem
            }
        }
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }
}

