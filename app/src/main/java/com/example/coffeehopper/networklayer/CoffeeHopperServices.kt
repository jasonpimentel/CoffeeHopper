package com.example.coffeehopper.networklayer

import com.example.coffeehopper.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

// object to hold all code to create a retrofit service.
object CoffeeHopperServices {

    private const val yelpApiBaseUrl ="https://api.yelp.com/v3/"

    private val yelpClient = OkHttpClient.Builder().addInterceptor(Interceptor {
        it.proceed(it.request().newBuilder().addHeader("Authorization", "Bearer ${BuildConfig.YELP_API_KEY}").build())
    }).build()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val yelpRetrofitService = Retrofit.Builder().client(yelpClient).addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
        yelpApiBaseUrl).build()

    val yelpApi: YelpApi by lazy {
        yelpRetrofitService.create(YelpApi::class.java)
    }

}