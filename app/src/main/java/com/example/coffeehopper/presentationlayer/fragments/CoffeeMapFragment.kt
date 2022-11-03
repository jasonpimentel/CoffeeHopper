package com.example.coffeehopper.presentationlayer.fragments

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.coffeehopper.R
import com.example.coffeehopper.databinding.FragmentCoffeeMapBinding
import com.example.coffeehopper.presentationlayer.viewmodels.CoffeeListMapViewModel
import com.example.coffeehopper.utils.foregroundAndBackgroundLocationPermissionApproved
import com.example.coffeehopper.utils.getPermissionRequestLauncher
import com.example.coffeehopper.utils.requestForegroundAndBackgroundLocationPermissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import org.koin.android.ext.android.inject

class CoffeeMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentCoffeeMapBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var map: GoogleMap

    private val viewModel by inject<CoffeeListMapViewModel>()

    @SuppressLint("MissingPermission")
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        requestPermissionLauncher = getPermissionRequestLauncher(binding.root) {
            map.isMyLocationEnabled = true
            zoomToLocation()
        }

        lifecycleScope.launchWhenCreated {
            map = mapFragment.awaitMap()
            if (!foregroundAndBackgroundLocationPermissionApproved()) {
                requestForegroundAndBackgroundLocationPermissions(requestPermissionLauncher)
            } else {
                map.isMyLocationEnabled = true
                zoomToLocation()
            }

            binding.searchButton.setOnClickListener {

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
        }

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        MapsInitializer.initialize(requireActivity())
    }

    @SuppressLint("MissingPermission")
    private fun zoomToLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            val lastKnownLocation: Location = it.result
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        lastKnownLocation.latitude,
                        lastKnownLocation.longitude
                    ), 12.0f
                )
            )
        }
    }
}