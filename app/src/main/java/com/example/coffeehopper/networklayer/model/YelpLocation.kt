package com.example.coffeehopper.networklayer.model

import com.squareup.moshi.Json

data class YelpLocation(
    val address1: String?,
    val address2: String?,
    val address3: String?,
    val city: String?,
    val country: String?,
    @Json(name ="display_address")
    val displayAddress: List<String>?,
    val state: String?,
    @Json(name = "zip_code")
    val zipCode: String?
)
