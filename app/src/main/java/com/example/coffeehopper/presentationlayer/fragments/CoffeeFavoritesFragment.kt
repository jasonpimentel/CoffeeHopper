package com.example.coffeehopper.presentationlayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coffeehopper.databinding.FragmentCoffeeFavoritesBinding
import com.example.coffeehopper.databinding.FragmentCoffeeListBinding
import com.example.coffeehopper.presentationlayer.viewmodels.CoffeeFavoritesViewModel
import org.koin.android.ext.android.inject

// use case
// a user will be able to use this fragment to see all their saved coffee spots
// they can click on one to see the details

class CoffeeFavoritesFragment: Fragment() {
    private val viewModel by inject<CoffeeFavoritesViewModel>()
    private lateinit var binding: FragmentCoffeeFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoffeeFavoritesBinding.inflate(inflater, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.coffeeHopperFavorites.observe(viewLifecycleOwner) {

        }
    }


}