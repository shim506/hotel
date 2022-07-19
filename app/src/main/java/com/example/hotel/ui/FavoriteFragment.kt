package com.example.hotel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotel.R
import com.example.hotel.data.ProductItem
import com.example.hotel.data.SortType
import com.example.hotel.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: LodgingListAdapter
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)

        setRecyclerview()

        listenFavoriteDataChange()
        listenSortTypeButtonClicked()

        return binding.root
    }

    private fun listenSortTypeButtonClicked() {
        binding.rbRecentSort.setOnClickListener {
            if (binding.rbRecentSort.isChecked) {
                activityViewModel.changeSortType(SortType.RECENT)
            }
        }
        binding.rbOldSort.setOnClickListener {
            if (binding.rbOldSort.isChecked) {
                activityViewModel.changeSortType(SortType.OLD)
            }
        }
        binding.rbGoodRateSort.setOnClickListener {
            if (binding.rbGoodRateSort.isChecked) {
                activityViewModel.changeSortType(SortType.GOOD_RATE)
            }
        }
        binding.rbBadRateSort.setOnClickListener {
            if (binding.rbBadRateSort.isChecked) {
                activityViewModel.changeSortType(SortType.BAD_RATE)
            }
        }
    }

    private fun listenFavoriteDataChange() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.favoriteList.collect {
                    adapter.submitList(it)
                    binding.rvFavoriteLodging.smoothScrollToPosition(0)
                }
            }
        }
    }

    private fun setRecyclerview() {
        val listener = object : AdapterItemTouchListener {
            override fun checkFavorite(product: ProductItem.Product) {
                activityViewModel.addFavorite(product)
            }

            override fun cancelFavorite(product: ProductItem.Product) {
                activityViewModel.cancelFavorite(product)
                activityViewModel.loadFavoriteData()
            }

            override fun getIntoDetail(product: ProductItem.Product) {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(product)
                findNavController().navigate(action)
            }
        }
        adapter = LodgingListAdapter(listener, activityViewModel.favoriteIdSet)
        binding.rvFavoriteLodging.adapter = adapter
        binding.rvFavoriteLodging.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

}