package com.example.hotel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.data.ProductItem
import com.example.hotel.data.repository.MainRepository
import com.example.hotel.data.repository.local.FavoriteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteDataSource: FavoriteDataSource
) : ViewModel() {

    private val _favoriteList = MutableStateFlow<List<ProductItem.Product>>(listOf())
    val favoriteList: StateFlow<List<ProductItem>> = _favoriteList

    fun loadFavoriteData() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteList.value = favoriteDataSource.getProducts()
        }
    }
}