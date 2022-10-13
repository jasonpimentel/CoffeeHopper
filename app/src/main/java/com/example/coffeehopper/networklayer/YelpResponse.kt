package com.example.coffeehopper.networklayer

data class YelpResponse(
    val total: Int,
    val businesses: List<YelpBusiness>
)