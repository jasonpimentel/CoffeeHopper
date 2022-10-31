package com.example.coffeehopper.networklayer.model

data class YelpResponse(
    val total: Int,
    val businesses: List<YelpBusiness>
)