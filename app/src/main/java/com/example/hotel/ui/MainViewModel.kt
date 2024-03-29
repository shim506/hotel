package com.example.hotel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.data.ProductItem
import com.example.hotel.data.SortType
import com.example.hotel.data.repository.MainRepository
import com.example.hotel.data.repository.local.FavoriteDataSource
import com.example.hotel.network.NetworkResult.Companion.LAST_PAGE
import com.example.hotel.network.onError
import com.example.hotel.network.onException
import com.example.hotel.network.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val favoriteDataSource: FavoriteDataSource
) : ViewModel() {
    private var pagingCount = 1

    private val _favoriteList = MutableStateFlow<List<ProductItem.Product>>(listOf())
    val favoriteList: StateFlow<List<ProductItem>> = _favoriteList

    private val _accumulateProductList = MutableStateFlow<List<ProductItem>>(listOf())
    val accumulateProductList: StateFlow<List<ProductItem>> = _accumulateProductList

    private val _errorMsg = MutableSharedFlow<String>()
    val errorMsg: SharedFlow<String> = _errorMsg

    private val _exceptionMessage = MutableSharedFlow<String>()
    val exceptionMessage: SharedFlow<String> = _exceptionMessage

    private val _nowSortType = MutableStateFlow<SortType>(SortType.RECENT)
    val nowSortType: StateFlow<SortType> = _nowSortType

    val favoriteIdSet = mutableSetOf<Int>()

    init {
        loadLodgingData()
        loadFavoriteData()
        setFavoriteIdSet()
    }

    fun loadFavoriteData() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteList.value = favoriteDataSource.getProducts()
        }
    }

    private fun setFavoriteIdSet() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteIdSet.addAll(favoriteDataSource.getProductIds())
        }
    }

    fun loadLodgingData() {
        viewModelScope.launch {
            if (_accumulateProductList.value.isEmpty() || _accumulateProductList.value.last() is ProductItem.LoadingBar) {
                val result = mainRepository.getData(pagingCount)
                result.onSuccess { data ->
                    data.data?.let { setProduct(it.product) }
                }.onError { code, msg ->
                    dealError(code, msg)
                }.onException { _ ->
                    _exceptionMessage.emit("데이터 요청 실패")
                }
            }
        }
    }

    private suspend fun dealError(code: Int, msg: String?) {
        _errorMsg.emit("$msg")
        if (code == LAST_PAGE) deleteLoadingData()
    }

    private fun setProduct(product: List<ProductItem.Product>) {
        if (_accumulateProductList.value.isNotEmpty()) deleteLoadingData()

        val mutableList = _accumulateProductList.value.toMutableList()
        mutableList.addAll(product)
        mutableList.add(ProductItem.LoadingBar())
        _accumulateProductList.value = mutableList
    }

    private fun deleteLoadingData() {
        val list = _accumulateProductList.value.toMutableList()
        list.removeLast()
        _accumulateProductList.value = list
    }

    fun addPageCount() {
        pagingCount++
    }

    fun addFavorite(product: ProductItem.Product) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDataSource.insertProduct(product)
            favoriteIdSet.add(product.id)
            loadFavoriteData()
        }

    }

    fun cancelFavorite(product: ProductItem.Product) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDataSource.deleteProduct(product)
            favoriteIdSet.remove(product.id)
            loadFavoriteData()
        }

    }

    fun changeSortType(goodRate: SortType) {
        viewModelScope.launch(Dispatchers.IO) {
            when (goodRate) {
                SortType.RECENT -> {
                    val list = favoriteDataSource.getProducts()
                    _favoriteList.value = list.reversed()
                }
                SortType.OLD -> {
                    loadFavoriteData()
                }
                SortType.GOOD_RATE -> {
                    _favoriteList.value = _favoriteList.value.sortedByDescending { it.rate }
                }
                else -> {
                    _favoriteList.value = _favoriteList.value.sortedBy { it.rate }
                }
            }
        }
    }
}