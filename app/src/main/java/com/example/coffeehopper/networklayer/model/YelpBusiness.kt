package com.example.coffeehopper.networklayer.model

import com.example.coffeehopper.datalayer.database.CoffeeHop
import com.squareup.moshi.Json
import java.util.Locale.Category

data class YelpBusiness(
    // place holder for coordinate object
    @Json(name ="categories")
    val categories: List<YelpCategory>?,
    val coordinates: YelpCoordinates?,
    @Json(name = "display_phone")
    val displayPhone: String?,
    val distance: String?,
    val id: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "is_closed")
    val isClosed: Boolean?,
    val location: YelpLocation?,
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
            address1 = it.location?.address1,
            address2 = it.location?.address2,
            address3 = it.location?.address3,
            city = it.location?.city,
            country = it.location?.country,
            state = it.location?.state,
            zip = it.location?.zipCode,
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
        address1 = location?.address1,
        address2 = location?.address2,
        address3 = location?.address3,
        city = location?.city,
        country = location?.country,
        state = location?.state,
        zip = location?.zipCode,
        name = name,
        price = price,
        phone = phone,
        rating = rating,
        reviewCount = reviewCount,
        url = url
    )

}

