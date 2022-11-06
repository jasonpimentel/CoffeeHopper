package com.example.coffeehopper.utils

import android.content.Context
import androidx.work.*
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class CoffeeHopperDbWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams), KoinComponent {

    private val repo: CoffeeHopperRepository by inject()

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    companion object {
        const val dbWorkerName = "databaseWorker"

        fun buildWorkRequest(constraints: Constraints): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<CoffeeHopperDbWorker>(3, TimeUnit.DAYS)
                .setConstraints(constraints)
                // Additional configuration
                .build()
        }
    }

    override fun doWork(): Result {
        // clear the db her
       coroutineScope.launch {
           try {
               repo.clearNonFavoriteCoffeeHops()
           } catch (exception: Exception) {
               cancel(exception.message.toString())
           }
        }
        return Result.success()
    }
}