package com.example.coffeehopper.utils

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.coffeehopper.datalayer.database.CoffeeHopperDao
import com.example.coffeehopper.datalayer.database.CoffeeHopperDatabase
import com.example.coffeehopper.datalayer.repository.CoffeeRepository
import com.example.coffeehopper.datalayer.repository.ICoffeeDataSource
import com.example.coffeehopper.datalayer.repository.LocalCoffeeDataSource
import com.example.coffeehopper.datalayer.repository.RemoteCoffeeDataSource

class ServiceLocator {
    // these are for testing. If these are set then return these.
    private var coffeeHopperDao: CoffeeHopperDao? = null

    private var localCoffeeDataSource: ICoffeeDataSource? = null

    private var remoteCoffeeDataSource: ICoffeeDataSource? = null

    fun provideCoffeeRepository(context: Context): CoffeeRepository {
        return CoffeeRepository(
            localDataSource = createLocalDataSource(context),
            remoteDataSource = createRemoteDataSource(context)
        )
    }

    private fun createDao(context: Context): CoffeeHopperDao {
        return coffeeHopperDao ?: createDatabase(context).coffeeHopperDao()
    }

    private fun createDatabase(context: Context): CoffeeHopperDatabase {
        return Room.databaseBuilder(context, CoffeeHopperDatabase::class.java, "coffeehopper.db")
            .build()
    }

    private fun createLocalDataSource(context: Context): ICoffeeDataSource {
        return LocalCoffeeDataSource()
    }

    private fun createRemoteDataSource(context: Context): ICoffeeDataSource {
        return RemoteCoffeeDataSource()
    }

    @VisibleForTesting
    private fun setLocalCoffeeDataSource(localCoffeeDataSource: ICoffeeDataSource) {
        this.localCoffeeDataSource = localCoffeeDataSource
    }

    @VisibleForTesting
    private fun setRemoteCoffeeDataSource(remoteCoffeeDataSource: ICoffeeDataSource) {
        this.remoteCoffeeDataSource = remoteCoffeeDataSource
    }

    @VisibleForTesting
    private fun setRemoteCoffeeHopperDao(coffeeHopperDao: CoffeeHopperDao) {
        this.coffeeHopperDao = coffeeHopperDao
    }

}