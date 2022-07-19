package com.example.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.hotel.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.tvDetail.text = args.product.toString()

        return binding.root
    }
}