package com.example.coffeehopper.datalayer.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoffeeHop::class], version = 1)
abstract class CoffeeHopperDatabase : RoomDatabase() {
    abstract fun coffeeHopperDao(): CoffeeHopperDao

    companion object {
        const val name = "coffee_hops"
    }
}

