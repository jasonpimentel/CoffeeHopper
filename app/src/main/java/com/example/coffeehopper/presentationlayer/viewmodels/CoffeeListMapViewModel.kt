package com.example.coffeehopper.presentationlayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository
import kotlinx.coroutines.launch

// use case outline
// this viewModel will be shared between a map view of the yelp data and a list view.
// a user can do a search and can favorite the one they like in the list view.
// this will save to the favorites table in the database.
class CoffeeListMapViewModel(private val repository: CoffeeHopperRepository): ViewModel() {
    val coffeeHops = repository.coffeeHops

    fun loadCoffeeHops(term: String = "coffee", latitude:Double, longitude: Double, radius: Int = 1609) {
        viewModelScope.launch {
            repository.loadCoffeeHops(term, latitude, longitude, radius)
        }
    }
}