package com.example.coffeehopper.utils

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.coffeehopper.datalayer.database.CoffeeHopperDao
import com.example.coffeehopper.datalayer.database.CoffeeHopperDatabase
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository
import com.example.coffeehopper.networklayer.CoffeeHopperServices
import com.example.coffeehopper.networklayer.YelpApi

object ServiceLocator {
    // these are for testing. If these are set then return these.
    @Volatile
    private var coffeeHopperDao: CoffeeHopperDao? = null

    private var yelpApi: YelpApi? = null

    @Volatile
    private var coffeeHopperRepository: CoffeeHopperRepository? = null

    fun provideCoffeeRepository(context: Context): CoffeeHopperRepository {
        synchronized(this) {
            return CoffeeHopperRepository(
                localDataSource = createDao(context),
                remoteDataSource = createYelpApi()
            )
        }
    }

    private fun createYelpApi(): YelpApi {
        return yelpApi ?: CoffeeHopperServices.yelpApi
    }

    private fun createDao(context: Context): CoffeeHopperDao {
        return coffeeHopperDao ?: createDatabase(context).coffeeHopperDao().also {
            synchronized(this) {
                coffeeHopperDao = it
            }
        }
    }

    private fun createDatabase(context: Context): CoffeeHopperDatabase {
        val result = Room.databaseBuilder(
            context,
            CoffeeHopperDatabase::class.java,
            "coffeehop.db"
        ).build()

        return result;
    }

    // functions below are for testing only. we can replace the with fakes to unit test
    @VisibleForTesting
    private fun setCoffeeRepository(coffeeHopperRepository: CoffeeHopperRepository) {
        this.coffeeHopperRepository = coffeeHopperRepository
    }

    @VisibleForTesting
    private fun setRemoteCoffeeHopperDao(coffeeHopperDao: CoffeeHopperDao) {
        this.coffeeHopperDao = coffeeHopperDao
    }

    @VisibleForTesting
    private fun setYelpApi(yelpApi: YelpApi) {
        this.yelpApi = yelpApi
    }
}