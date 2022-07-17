package com.example.hotel.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotel.R
import com.example.hotel.databinding.FragmentAllBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllFragment : Fragment() {
    private lateinit var binding: FragmentAllBinding
    private val adapter = LodgingListAdapter()
    private val activityViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all, container, false)

        setRecyclerview()
        listenPagingProductChange()

        listeningErrorDataChange()
        listeningExceptionDataChange()


        binding.rvAllLodging.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                // 스크롤이 끝에 도달했는지 확인
                if (!binding.rvAllLodging.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    Log.d("test111", "sd")
                    activityViewModel.addPageCount()
                    activityViewModel.loadLodgingData()
                }
            }
        })


        return binding.root
    }

    private fun listenPagingProductChange() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.accumulateProductList.collect {
                    if (it.isNotEmpty()) {
                        Log.d("test11", "110")
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun setRecyclerview() {
        binding.rvAllLodging.adapter = adapter
        binding.rvAllLodging.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun listeningExceptionDataChange() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.exceptionMessage?.collect {
                    if (it.isNotEmpty()) {
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun listeningErrorDataChange() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.errorMsg?.collect {
                    if (it.isNotEmpty()) {
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}