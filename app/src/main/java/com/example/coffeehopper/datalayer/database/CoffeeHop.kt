package com.example.coffeehopper.datalayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CoffeeHop.tableName)
data class CoffeeHop(
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "display_phone") val displayPhone: String?,
    @ColumnInfo(name = "distance") val distance: String?,
    @PrimaryKey() val id: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "is_closed") val isClosed: Boolean?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "rating") val rating: Float?,
    @ColumnInfo(name = "review_count") val reviewCount: Int?,
    @ColumnInfo(name = "url") val url: String?
) {
    companion object {
        const val tableName = "coffee_hops"
    }
}
