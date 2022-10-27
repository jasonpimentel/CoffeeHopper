package com.example.coffeehopper.datalayer.repository

import androidx.lifecycle.LiveData
import com.example.coffeehopper.datalayer.database.CoffeeHop
import com.example.coffeehopper.datalayer.database.CoffeeHopperDao
import com.example.coffeehopper.networklayer.YelpApi
import com.example.coffeehopper.networklayer.YelpResponse
import com.example.coffeehopper.networklayer.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CoffeeHopperRepository(
    private val localDataSource: CoffeeHopperDao,
    private val remoteDataSource: YelpApi
) {
    val coffeeHops: LiveData<List<CoffeeHop>> = localDataSource.getAllCoffeeHops()

    val coffeeHopFavorites: LiveData<List<CoffeeHop>> = localDataSource.getCoffeeHopFavorites()

    // offline caching
    // when the data gets loaded from the internet it will also update the cache
    suspend fun loadCoffeeHops(term: String, latitude: Double, longitude: Double, radius: Int) {
        withContext(Dispatchers.IO) {
            val yelpBusinesses: YelpResponse =
                remoteDataSource.searchForBusinesses(term, latitude, longitude, radius)
            localDataSource.addCoffeeHops(yelpBusinesses.businesses.asDbModel())
        }
    }

    // basically this is just setting the favorite boolean to true.
    suspend fun addCoffeeHopFavorite(coffeeHop: CoffeeHop) {
        withContext(Dispatchers.IO) {
            localDataSource.updateCoffeeHop(coffeeHop)
        }
    }
}