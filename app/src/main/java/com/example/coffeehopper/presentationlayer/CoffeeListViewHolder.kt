package com.example.coffeehopper.presentationlayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeehopper.R
import com.example.coffeehopper.databinding.CoffeeItemViewBinding
import com.example.coffeehopper.networklayer.YelpBusiness

class CoffeeListViewHolder(private val binding: CoffeeItemViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindView(yelp: YelpBusiness) {
        binding.yelpBusiness = yelp
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CoffeeListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CoffeeItemViewBinding.inflate(layoutInflater, parent, false)
            return CoffeeListViewHolder(binding)
        }
    }
}