package com.example.coffeehopper.datalayer.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CoffeeHopperDao {

    @Query("Select * from ${CoffeeHop.tableName}")
    fun getAllCoffeeHops(): LiveData<List<CoffeeHop>>

    @Query("select * from ${CoffeeHop.tableName} where id is :id ")
    fun getCoffeeHop(id: Int): CoffeeHop

    @Query("select * from ${CoffeeHop.tableName} where favorite is 1")
    fun getCoffeeHopFavorites() : LiveData<List<CoffeeHop>>

    // if we pull up the same key from yelp then its okay to just replace it as it may be new data.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCoffeeHops(coffeeHops: List<CoffeeHop>)

    // use this to mark a coffee hop as favorite by updating the favorite column
    @Update
    suspend fun updateCoffeeHop(coffeeHop: CoffeeHop)


}