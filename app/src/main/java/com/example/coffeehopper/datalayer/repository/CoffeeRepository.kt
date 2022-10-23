package com.example.coffeehopper.datalayer.repository

//
class CoffeeRepository(
    private val localDataSource: ICoffeeDataSource,
    private val remoteDataSource: ICoffeeDataSource
) {

}