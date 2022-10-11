package com.example.coffeehopper

import android.app.Application
import timber.log.Timber

class CoffeeHopperApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupServiceLocator()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun setupServiceLocator() {

    }
}