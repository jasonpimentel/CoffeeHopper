package com.example.coffeehopper.presentationlayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository
import kotlinx.coroutines.launch

class CoffeeFavoritesViewModel(private val coffeeHopperRepository: CoffeeHopperRepository) :
    ViewModel() {

    val coffeeHopperFavorites = coffeeHopperRepository.coffeeHopFavorites

    fun loadCoffeeHopFavorite() {
        viewModelScope.launch {
            coffeeHopperRepository.getCoffeeHopFavorite()
        }
    }
}