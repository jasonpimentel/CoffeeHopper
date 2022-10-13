package com.example.coffeehopper.networklayer

import com.squareup.moshi.Json

data class YelpBusiness(
    // place holder for coordinate object
    val coordinates: YelpCoordinates,
    @Json(name = "display_phone")
    val displayPhone: String?,
    val distance: String?,
    val id: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "is_closed")
    val isClosed: Boolean?,
    //place holder for object
    val Location: YelpLocation,
    val name: String?,
    val phone: String?,
    @Json(name = "price")
    val price: String?,
    val rating: Float?,
    @Json(name = "review_count")
    val reviewCount: Int?,
    val url: String?
)