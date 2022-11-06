package com.example.coffeehopper.presentationlayer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeehopper.datalayer.database.CoffeeHop
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoffeeDetailsViewModel(private val repository: CoffeeHopperRepository) : ViewModel() {
    var coffeeHop: CoffeeHop? = null

    private val _favorited: MutableLiveData<Boolean> = MutableLiveData()

    val favorited: LiveData<Boolean>
        get() = _favorited

    fun setFavorited(isFavorite: Boolean) {
        _favorited.value = isFavorite
    }

    fun addFavorite(coffeeHop: CoffeeHop) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                repository.updateCoffeeHop(coffeeHop.copy(favorite = true))
            }
        }
    }

    fun removeFavorite(coffeeHop: CoffeeHop) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateCoffeeHop(coffeeHop.copy(favorite = false))
            }
        }
    }

}

