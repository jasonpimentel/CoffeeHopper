package com.example.coffeehopper.networklayer.model

import com.squareup.moshi.Json

data class YelpCategory(
    @Json(name ="alias") val alias: String?,
    @Json(name = "title") val title: String?)
