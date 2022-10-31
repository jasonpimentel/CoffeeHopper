package com.example.coffeehopper.presentationlayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.coffeehopper.R
import com.example.coffeehopper.databinding.FragmentCoffeeMapBinding
import com.example.coffeehopper.presentationlayer.viewmodels.CoffeeListMapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import org.koin.android.ext.android.inject

class CoffeeMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentCoffeeMapBinding

    private lateinit var map: GoogleMap

    private val viewModel by inject<CoffeeListMapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoffeeMapBinding.inflate(inflater)
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        viewModel.coffeeHops.observe(viewLifecycleOwner) { coffeeHops ->
            for (coffeeHop in coffeeHops) {
                // add marker to map
                map.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            coffeeHop.latitude!!,
                            coffeeHop.longitude!!
                        )
                    ).title(coffeeHop.name)
                )
            }
        }

        lifecycleScope.launchWhenCreated {
            map = mapFragment.awaitMap()

            viewModel.centerMapPosition = map.cameraPosition.target
            // val visibleRegion = map.projection.visibleRegion.latLngBounds


            // Location.distanceBetween(visibleRegion.center.latitude, visibleRegion.center.longitude, midPoint, visibleRegion.northeast.longitude)

              /*
                map.setOnCameraMoveStartedListener { reason ->
                if (reason == OnCameraMoveStartedListener.REASON_GESTURE) {
                    binding.searchButton.visibility = View.VISIBLE
                    viewModel.centerMapPosition = map.cameraPosition.target
                }
            }*/
        }

        binding.searchButton.setOnClickListener {
            //viewModel.centerMapPosition?.let { center ->

                val radius =
                    viewModel.calculateVisibleRadius(map.projection.visibleRegion.latLngBounds)
                viewModel.loadCoffeeHops(
                    latitude = map.projection.visibleRegion.latLngBounds.center.latitude,
                    longitude = map.projection.visibleRegion.latLngBounds.center.longitude,
                    radius = radius.toInt()
                )
                it.visibility = View.GONE
                map.clear()
            }
        // }

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        MapsInitializer.initialize(requireActivity())
    }
}