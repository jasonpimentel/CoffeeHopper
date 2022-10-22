package com.example.coffeehopper.presentationlayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.coffeehopper.utils.FireBaseLiveData

class AuthenticationViewModel: ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    val authenticationState = FireBaseLiveData().map { user->
        if(user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

}