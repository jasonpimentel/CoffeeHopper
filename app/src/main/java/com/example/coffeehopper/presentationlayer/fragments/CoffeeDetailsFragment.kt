package com.example.coffeehopper.presentationlayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.coffeehopper.databinding.FragmentCoffeeDetailsBinding
import com.example.coffeehopper.databinding.FragmentCoffeeListBinding

class CoffeeDetailsFragment: Fragment() {

    val args: CoffeeDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentCoffeeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCoffeeDetailsBinding.inflate(inflater)

        return binding.root
    }
}