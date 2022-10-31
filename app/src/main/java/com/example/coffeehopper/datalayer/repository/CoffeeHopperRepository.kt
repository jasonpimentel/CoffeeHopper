package com.example.coffeehopper.datalayer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeehopper.datalayer.database.CoffeeHop
import com.example.coffeehopper.datalayer.database.CoffeeHopperDao
import com.example.coffeehopper.networklayer.YelpApi
import com.example.coffeehopper.networklayer.model.YelpResponse
import com.example.coffeehopper.networklayer.model.asDbModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


class CoffeeHopperRepository(
    private val localDataSource: CoffeeHopperDao,
    private val remoteDataSource: YelpApi
) {
    private val _coffeeHops: MutableLiveData<List<CoffeeHop>> = MutableLiveData()

    val coffeeHopFavorites: LiveData<List<CoffeeHop>> = localDataSource.getCoffeeHopFavorites()

    val coffeeHops: LiveData<List<CoffeeHop>>
    get() = _coffeeHops

    // offline caching
    // when the data gets loaded from the internet it will also update the cache
    suspend fun loadCoffeeHops(term: String, centerLatLng: LatLng, targetNorthEast: LatLng, targetSouthWest: LatLng, radius: Int) {
        withContext(Dispatchers.IO) {
            val yelpResponse: YelpResponse =
                remoteDataSource.searchForBusinesses(term, centerLatLng.latitude, centerLatLng.longitude, radius)
            localDataSource.addCoffeeHops(yelpResponse.businesses.asDbModel())
            val localData = localDataSource.getCoffeeHopsWithInBounds(targetSouthWest.latitude, targetNorthEast.latitude, targetSouthWest.longitude, targetNorthEast.longitude)
            withContext(Dispatchers.Main) {
                _coffeeHops.value = localData
            }

            Timber.d("found ${yelpResponse.total} businesses")
        }
    }

    // basically this is just setting the favorite boolean to true.
    suspend fun addCoffeeHopFavorite(coffeeHop: CoffeeHop) {
        withContext(Dispatchers.IO) {
            localDataSource.updateCoffeeHop(coffeeHop)
        }
    }
}