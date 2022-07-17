package com.example.hotel.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.data.ProductItem
import com.example.hotel.data.repository.MainRepository
import com.example.hotel.network.NetworkResult.Companion.LAST_PAGE
import com.example.hotel.network.onError
import com.example.hotel.network.onException
import com.example.hotel.network.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    private var pagingCount = 1

    private val _accumulateProductList = MutableStateFlow<List<ProductItem>>(listOf())
    val accumulateProductList: StateFlow<List<ProductItem>> = _accumulateProductList

    private val _errorMsg = MutableSharedFlow<String>()
    val errorMsg: SharedFlow<String> = _errorMsg

    private val _exceptionMessage = MutableSharedFlow<String>()
    val exceptionMessage: SharedFlow<String> = _exceptionMessage

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
}