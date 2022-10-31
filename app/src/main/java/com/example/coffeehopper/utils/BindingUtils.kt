package com.example.coffeehopper.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coffeehopper.R
import timber.log.Timber

@BindingAdapter("coffeeHopImgUrl")
fun bindImage(view: ImageView, coffeeHopImgUrl: String?) {
    if(!coffeeHopImgUrl.isNullOrEmpty()) {
        val imgUri = coffeeHopImgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(view.context).load(imgUri).apply(
            RequestOptions()
                .error(R.drawable.ic_outline_broken_image_24)
        ).into(view)
    } else {
        view.setImageResource(R.drawable.ic_outline_image_24)
    }
}

@BindingAdapter(value = ["address1", "city", "state", "zip"])
fun bindAddress(view: TextView, address1: String?, city: String?, state: String?, zip: String?) {
    val formattedAddress = "$address1 \n $city, $state $zip"
    view.text = formattedAddress
}

@BindingAdapter("favoriteVisibility")
fun bindFavoriteVisibility(view: ImageView, favoriteVisibility: Boolean){
    if(!favoriteVisibility) {
        view.visibility = View.INVISIBLE
    }
    else{
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("rating")
fun bindRating(view: TextView, rating: Float) {
    view.text = "Rating: $rating/5"
}