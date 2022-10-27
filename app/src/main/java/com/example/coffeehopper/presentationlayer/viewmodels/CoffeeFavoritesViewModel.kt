package com.example.coffeehopper.presentationlayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository
import kotlinx.coroutines.launch

class CoffeeFavoritesViewModel(coffeeHopperRepository: CoffeeHopperRepository): ViewModel() {

    val coffeeHopperFavorites = coffeeHopperRepository.coffeeHopFavorites

}