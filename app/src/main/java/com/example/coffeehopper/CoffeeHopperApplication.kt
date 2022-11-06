package com.example.coffeehopper

import android.app.Application
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository
import com.example.coffeehopper.presentationlayer.viewmodels.AuthenticationViewModel
import com.example.coffeehopper.presentationlayer.viewmodels.CoffeeDetailsViewModel
import com.example.coffeehopper.presentationlayer.viewmodels.CoffeeFavoritesViewModel
import com.example.coffeehopper.presentationlayer.viewmodels.CoffeeListMapViewModel
import com.example.coffeehopper.utils.ServiceLocator
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class CoffeeHopperApplication : Application() {

    private val coffeeHopperRepository: CoffeeHopperRepository
    get() = ServiceLocator.provideCoffeeRepository(this)

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupKoin()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun setupKoin() {
        val module = module {
            viewModel {
                CoffeeFavoritesViewModel(get())
            }

            viewModel {
                CoffeeDetailsViewModel(get())
            }
            single { AuthenticationViewModel() }
            single { coffeeHopperRepository }

            single { CoffeeListMapViewModel(get()) }
        }

        startKoin {
            androidContext(this@CoffeeHopperApplication)
            modules(module)
        }
    }
}