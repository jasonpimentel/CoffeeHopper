package com.example.coffeehopper.datalayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_hops")
data class CoffeeHop(
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "display_phone") val displayPhone: String?,
    @PrimaryKey() val id: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "address1") val address1: String?,
    @ColumnInfo(name = "address2") val address2: String?,
    @ColumnInfo(name = "address3") val address3: String?,
    @ColumnInfo(name = "state") val state: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "zip") val zip: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "rating") val rating: Float?,
    @ColumnInfo(name = "review_count") val reviewCount: Int?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "favorite") val favorite: Boolean? = false
) {
    companion object {
        const val tableName = "coffee_hops"
    }
}
