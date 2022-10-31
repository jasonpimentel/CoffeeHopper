package com.example.coffeehopper.presentationlayer.viewmodels

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository
import com.firebase.ui.auth.util.ui.FlowUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.launch
import kotlin.math.sqrt

// use case outline
// this viewModel will be shared between a map view of the yelp data and a list view.
// a user can do a search and can favorite the one they like in the list view.
// this will save to the favorites table in the database.
class CoffeeListMapViewModel(private val repository: CoffeeHopperRepository): ViewModel() {
    val coffeeHops = repository.coffeeHops

    var centerMapPosition: LatLng? = null

    fun loadCoffeeHops(term: String = "coffee", latitude:Double, longitude: Double, radius: Int = 1609) {

        viewModelScope.launch {
            // calculate bounds to query the db
            val latLngCenterPosition = LatLng(latitude, longitude)
            val targetNorthEast = SphericalUtil.computeOffset(latLngCenterPosition, radius.toDouble() * sqrt(2.0), 45.0)
            val targetSouthWest = SphericalUtil.computeOffset(latLngCenterPosition, radius.toDouble() * sqrt(2.0), 225.0)

            // the repository can take the bounds as well when "loading" the data.
            // in reality we will do a search on the interwebs for the data. load it into the cache
            // then we will query the data with a bound and return LiveData. this way we can display data as a list as well.
            repository.loadCoffeeHops(term, latLngCenterPosition, targetNorthEast, targetSouthWest, radius)
        }
    }

    fun calculateVisibleRadius(visibleRegion: LatLngBounds): Float {
        val midPoint = ((visibleRegion.northeast.latitude + visibleRegion.southwest.latitude)/2.0)
        val start = Location("point a").apply {
            longitude = visibleRegion.center.longitude
            latitude = visibleRegion.center.latitude
        }

        val end = Location("point b").apply {
            longitude = visibleRegion.northeast.longitude
            latitude = midPoint
        }

        return start.distanceTo(end)
    }
}