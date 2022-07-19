package com.example.hotel.ui

import androidx.lifecycle.ViewModel
import com.example.hotel.data.repository.local.FavoriteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteDataSource: FavoriteDataSource
) : ViewModel() {



}