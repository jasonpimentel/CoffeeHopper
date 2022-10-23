package com.example.coffeehopper.presentationlayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeehopper.R
import com.example.coffeehopper.networklayer.YelpBusiness

class CoffeeListAdapter(): ListAdapter<YelpBusiness, CoffeeListViewHolder>(CoffeeListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeListViewHolder {
       return CoffeeListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CoffeeListViewHolder, position: Int) {
       val item: YelpBusiness = getItem(position)
        holder.bindView(item)
    }
}

class CoffeeListDiffUtil: DiffUtil.ItemCallback<YelpBusiness>() {
    override fun areItemsTheSame(oldItem: YelpBusiness, newItem: YelpBusiness): Boolean {
       return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: YelpBusiness, newItem: YelpBusiness): Boolean {
        return oldItem == newItem
    }


}
