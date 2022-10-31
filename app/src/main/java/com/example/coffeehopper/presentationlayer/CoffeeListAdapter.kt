package com.example.coffeehopper.presentationlayer

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.coffeehopper.datalayer.database.CoffeeHop

class CoffeeListAdapter(private val clickListener: CoffeeHopListener): ListAdapter<CoffeeHop, CoffeeListViewHolder>(CoffeeListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeListViewHolder {
       return CoffeeListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CoffeeListViewHolder, position: Int) {
       val item: CoffeeHop = getItem(position)
        holder.bindView(item, clickListener)
    }
}

class CoffeeListDiffUtil: DiffUtil.ItemCallback<CoffeeHop>() {
    override fun areItemsTheSame(oldItem: CoffeeHop, newItem: CoffeeHop): Boolean {
       return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CoffeeHop, newItem: CoffeeHop): Boolean {
        return oldItem == newItem
    }

}

class CoffeeHopListener(val coffeeClickListener: (coffeeHop: CoffeeHop) -> Unit) {
    fun coffeeHopClick(coffeeHop: CoffeeHop) = coffeeClickListener(coffeeHop)
}
