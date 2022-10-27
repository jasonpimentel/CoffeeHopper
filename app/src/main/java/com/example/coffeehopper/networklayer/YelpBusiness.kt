package com.example.coffeehopper.networklayer

import com.example.coffeehopper.datalayer.database.CoffeeHop
import com.squareup.moshi.Json

data class YelpBusiness(
    // place holder for coordinate object
    val coordinates: YelpCoordinates?,
    @Json(name = "display_phone")
    val displayPhone: String?,
    val distance: String?,
    val id: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "is_closed")
    val isClosed: Boolean?,
    //place holder for object
    val Location: YelpLocation?,
    val name: String?,
    val phone: String?,
    @Json(name = "price")
    val price: String?,
    val rating: Float?,
    @Json(name = "review_count")
    val reviewCount: Int?,
    val url: String?
)

fun List<YelpBusiness>.asDbModel(): List<CoffeeHop>{
    return map {
        CoffeeHop(
            id = it.id!!,
            latitude = it.coordinates?.latitude,
            longitude = it.coordinates?.longitude,
            displayPhone = it.displayPhone,
            imageUrl = it.imageUrl,
            address1 = it.Location?.address1,
            address2 = it.Location?.address2,
            address3 = it.Location?.address3,
            city = it.Location?.city,
            country = it.Location?.country,
            state = it.Location?.state,
            zip = it.Location?.zipCode,
            name = it.name,
            price = it.price,
            phone = it.phone,
            rating = it.rating,
            reviewCount = it.reviewCount,
            url = it.url
        )
    }
}

fun YelpBusiness.asDbModel(): CoffeeHop {
    return CoffeeHop(
        id = id!!,
        latitude = coordinates?.latitude,
        longitude = coordinates?.longitude,
        displayPhone = displayPhone,
        imageUrl = imageUrl,
        address1 = Location?.address1,
        address2 = Location?.address2,
        address3 = Location?.address3,
        city = Location?.city,
        country = Location?.country,
        state = Location?.state,
        zip = Location?.zipCode,
        name = name,
        price = price,
        phone = phone,
        rating = rating,
        reviewCount = reviewCount,
        url = url
    )

}

