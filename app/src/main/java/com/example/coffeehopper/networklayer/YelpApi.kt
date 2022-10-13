package com.example.coffeehopper.networklayer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// interface required by retrofit to define retrofit service for specific api
interface YelpApi {

    @GET("businesses/search")
    suspend fun searchForBusiness(@Query("term") term: String, @Query("latitude") latitude: Double, @Query("longitude") longitude: Double) : YelpResponse
}