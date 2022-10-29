package com.example.coffeehopper.presentationlayer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeehopper.databinding.FragmentCoffeeListBinding
import com.example.coffeehopper.presentationlayer.CoffeeHopListener
import com.example.coffeehopper.presentationlayer.CoffeeListAdapter
import com.example.coffeehopper.presentationlayer.activities.CoffeeDetailsActivity
import com.example.coffeehopper.presentationlayer.viewmodels.CoffeeListMapViewModel
import com.example.coffeehopper.utils.ServiceLocator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

// use case
// user will be able to see a list of coffee shops near by from the yelp api
class CoffeeListFragment: Fragment() {

    private lateinit var binding: FragmentCoffeeListBinding

    private val viewModel by inject<CoffeeListMapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoffeeListBinding.inflate(inflater, container, false)
        binding.coffeeListRecycler.adapter = CoffeeListAdapter(CoffeeHopListener { coffeeHop ->
           val intent = Intent(requireContext(), CoffeeDetailsActivity::class.java)
            intent.putExtra("coffeeHop", coffeeHop)
            startActivity(intent)
        })
        binding.coffeeListRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.loadCoffeeHops(latitude = 47.37778345227484, longitude = -122.2976992311103)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.coffeeHops.observe(viewLifecycleOwner) {
            (binding.coffeeListRecycler.adapter as CoffeeListAdapter).submitList(it)
        }
    }
}