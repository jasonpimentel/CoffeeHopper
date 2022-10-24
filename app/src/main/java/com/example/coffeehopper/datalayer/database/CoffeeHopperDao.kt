package com.example.coffeehopper.datalayer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CoffeeHopperDao {

    @Query("Select * from ${CoffeeHop.tableName}")
    fun getAllCoffeeHops(): List<CoffeeHop>

    @Query("select * from ${CoffeeHop.tableName} where id is :id ")
    fun getCoffeeHop(id: Int): CoffeeHop

    @Insert
    fun addCoffeeHop(coffeeHop: CoffeeHop)

    @Insert
    fun addCoffeeHops(coffeeHops: List<CoffeeHop>)

    @Delete
    fun deleteCoffeeHop(coffeeHop: CoffeeHop)
}