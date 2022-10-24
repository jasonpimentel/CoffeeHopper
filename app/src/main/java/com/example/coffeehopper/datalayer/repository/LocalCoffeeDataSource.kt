package com.example.coffeehopper.datalayer.repository

import com.example.coffeehopper.datalayer.database.CoffeeHopperDao

class LocalCoffeeDataSource(private val coffeeHopperDao: CoffeeHopperDao) : ICoffeeDataSource {

}