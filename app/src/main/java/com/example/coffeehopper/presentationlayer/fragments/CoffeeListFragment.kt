package com.example.coffeehopper.presentationlayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coffeehopper.databinding.FragmentCoffeeListBinding
import com.example.coffeehopper.presentationlayer.CoffeeListAdapter
import com.example.coffeehopper.presentationlayer.viewmodels.CoffeeListMapViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// use case
// user will be able to see a list of coffee shops near by from the yelp api
class CoffeeListFragment: Fragment() {

    private lateinit var binding: FragmentCoffeeListBinding

    private val viewModel by viewModel<CoffeeListMapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoffeeListBinding.inflate(inflater, container, false)
        binding.coffeeListRecycler.adapter = CoffeeListAdapter()
        return binding.root
    }
}