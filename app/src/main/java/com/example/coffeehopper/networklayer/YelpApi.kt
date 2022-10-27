package com.example.coffeehopper.networklayer

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

// interface required by retrofit to define retrofit service for specific api
interface YelpApi {

    @GET("businesses/search")
    suspend fun searchForBusinesses(@Query("term") term: String, @Query("latitude") latitude: Double, @Query("longitude") longitude: Double, @Query("radius") radius: Int) : YelpResponse

    @GET("businesses/matches")
    suspend fun getBusiness(@Query("name") name: String, @Query("address1") address1: String, @Query("city") city: String, @Query("country") country: String, @Query("id") id: Int): YelpBusiness
}