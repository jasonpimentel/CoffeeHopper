package com.example.coffeehopper.datalayer.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoffeeHop::class], version = 1,  exportSchema = false)
abstract class CoffeeHopperDatabase : RoomDatabase() {
    abstract fun coffeeHopperDao(): CoffeeHopperDao
}

