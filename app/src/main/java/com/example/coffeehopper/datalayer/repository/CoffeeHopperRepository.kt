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

    private val _coffeeHopFavorites = MutableLiveData<List<CoffeeHop>>()

    val coffeeHopFavorites: LiveData<List<CoffeeHop>>
    get() = _coffeeHopFavorites

    val coffeeHops: LiveData<List<CoffeeHop>>
        get() = _coffeeHops

    // offline caching
    // when the data gets loaded from the internet it will also update the cache
    suspend fun loadCoffeeHops(
        term: String,
        centerLatLng: LatLng,
        targetNorthEast: LatLng,
        targetSouthWest: LatLng,
        radius: Int
    ) {
        withContext(Dispatchers.IO) {
            val yelpResponse: YelpResponse =
                remoteDataSource.searchForBusinesses(
                    term,
                    centerLatLng.latitude,
                    centerLatLng.longitude,
                    radius
                )
            localDataSource.addCoffeeHops(yelpResponse.businesses.asDbModel())
            val localData = localDataSource.getCoffeeHopsWithInBounds(
                targetSouthWest.latitude,
                targetNorthEast.latitude,
                targetSouthWest.longitude,
                targetNorthEast.longitude
            )
            withContext(Dispatchers.Main) {
                _coffeeHops.value = localData
            }

            Timber.d("found ${yelpResponse.total} businesses")
        }
    }

    fun getCoffeeHop(id: String): CoffeeHop {
        return localDataSource.getCoffeeHop(id)
    }

    suspend fun getCoffeeHopFavorite() {
        withContext(Dispatchers.IO) {
            val coffeeHopFavorites = localDataSource.getCoffeeHopFavorites()
            withContext(Dispatchers.Main) {
                _coffeeHopFavorites.value = coffeeHopFavorites
            }
        }
    }

    suspend fun clearNonFavoriteCoffeeHops() {
        val nonFavorites = localDataSource.getCoffeeHopNonFavorites()
        localDataSource.deleteCoffeeHops(nonFavorites)
    }

    // basically this is just setting the favorite boolean to true.
    suspend fun updateCoffeeHop(coffeeHop: CoffeeHop) {
        withContext(Dispatchers.IO) {
            localDataSource.updateCoffeeHop(coffeeHop)
        }
    }
}