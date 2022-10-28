package com.example.coffeehopper.presentationlayer.viewmodels

import androidx.lifecycle.ViewModel
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository

class CoffeeFavoritesViewModel(coffeeHopperRepository: CoffeeHopperRepository) : ViewModel() {

    val coffeeHopperFavorites = coffeeHopperRepository.coffeeHopFavorites

}