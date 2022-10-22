package com.example.coffeehopper

import android.app.Application
import com.example.coffeehopper.presentationlayer.viewmodels.AuthenticationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class CoffeeHopperApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupServiceLocator()
        setupKoin()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun setupServiceLocator() {

    }

    private fun setupDependencyInjection() {

    }

    private fun setupKoin() {
        val module = module {
            single { AuthenticationViewModel() }
        }

        startKoin {
            androidContext(this@CoffeeHopperApplication)
            modules(module)
        }
    }
}